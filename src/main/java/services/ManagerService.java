
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Manager;
import domain.Services;
import forms.RegistrationForm;

@Service
@Transactional
public class ManagerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ManagerRepository	managerRepository;
	
	@Autowired
	private Md5PasswordEncoder	encoder;
	
	@Autowired
	private Validator			validator;


	// Supporting services ----------------------------------------------------
	
	@Autowired
	private UtilityService utilityService;

	// Constructors -----------------------------------------------------------

	public ManagerService() {
		super();
	}

	// CRUD Services ----------------------------------------------------------
	
	public Manager create() {
		Manager result;
		UserAccount userAccount;
		Collection<Authority> authorities;
		Authority authority;

		authority = new Authority();
		authority.setAuthority(Authority.MANAGER);
		authorities = new HashSet<>();
		authorities.add(authority);
		userAccount = new UserAccount();
		userAccount.setAuthorities(authorities);

		result = new Manager();
		result.setServices(Collections.<Services> emptySet());
		result.setUserAccount(userAccount);
		result.setVAT(this.utilityService.generateValidVAT());

		return result;
	}

	public void save(final Manager manager) {
		String password, hash;

		password = manager.getUserAccount().getPassword();
		hash = this.encoder.encodePassword(password, null);
		manager.getUserAccount().setPassword(hash);

		this.managerRepository.save(manager);
	}

	public Collection<Manager> findAll() {
		Collection<Manager> result;

		result = this.managerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Manager findOne(final int managerId) {
		Manager result;

		result = this.managerRepository.findOne(managerId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods -------------------------------------------------
	
	public Manager reconstruct(final RegistrationForm registrationForm, final BindingResult binding) {
		Manager result;
		UserAccount userAccount;

		result = this.create();
		result.setName(registrationForm.getName());
		result.setSurname(registrationForm.getSurname());
		result.setEmail(registrationForm.getEmail());
		result.setPhoneNumber(registrationForm.getPhoneNumber());
		result.setPostalAddress(registrationForm.getPostalAddress());
		result.setBirthdate(registrationForm.getBirthdate());
		

		userAccount = result.getUserAccount();
		userAccount.setUsername(registrationForm.getUserAccount().getUsername());
		userAccount.setPassword(registrationForm.getUserAccount().getPassword());

		this.validateRegistration(result, registrationForm, binding);

		return result;
	}

	private void validateRegistration(final Manager manager, final RegistrationForm registrationForm, final BindingResult binding) {
		String password, confirmPassword;

		password = registrationForm.getUserAccount().getPassword();
		confirmPassword = registrationForm.getConfirmPassword();

		if (!password.equals(confirmPassword))
			binding.rejectValue("confirmPassword", "manager.missmatch.password", "Does not match with password");

		if (!"accept".equals(registrationForm.getAgreement()))
			binding.rejectValue("agreement", "manager.termsAndConditions.rejected", "Must be accepted");

		this.validator.validate(manager, binding);
	}

	protected void addService(final Manager manager, final Services service) {
		Collection<Services> aux;

		aux = new HashSet<>(manager.getServices());
		aux.add(service);
		manager.setServices(aux);
	}

	protected void removeService(final Manager manager, final Services service) {
		Collection<Services> aux;

		aux = new HashSet<>(manager.getServices());
		aux.remove(service);
		manager.setServices(aux);
	}

	public Manager findByPrincipal() {
		Manager result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		result = this.managerRepository.findByUserAccountId(userAccount.getId());

		return result;
	}
	
	public Collection<String> findAllVATs(){
		Collection<String> result;
		
		result = this.managerRepository.findAllVATs();
		
		return result;
	}

}
