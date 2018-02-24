
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

import repositories.UserRepository;
import security.Authority;
import security.UserAccount;
import domain.Comment;
import domain.RSVP;
import domain.Rendezvous;
import domain.User;
import forms.RegistrationForm;

@Service
@Transactional
public class UserService {

	// Managed repository ---------------------------------------------------

	@Autowired
	private UserRepository	userRepository;

	// Supporting services --------------------------------------------------

	// TODO: comentamos esto de prueba
	//@Autowired
	//private Validator			validator;

	@Autowired
	Md5PasswordEncoder		encoder;


	// Constructors ---------------------------------------------------------

	public UserService() {
		super();
	}

	// CRUD methods ---------------------------------------------------------

	public User create() {
		User result;
		UserAccount userAccount;
		Collection<Authority> authorities;
		Authority authority;

		authority = new Authority();
		authority.setAuthority(Authority.USER);
		authorities = new HashSet<>();
		authorities.add(authority);
		userAccount = new UserAccount();
		userAccount.setAuthorities(authorities);

		result = new User();
		result.setComments(Collections.<Comment> emptySet());
		result.setCreatedRendezvouses(Collections.<Rendezvous> emptySet());
		result.setReserves(Collections.<RSVP> emptySet());
		result.setUserAccount(userAccount);

		return result;
	}

	public void save(final User user) {
		final String password, hash;
		/*
		 * TODO: comentamos esto de prueba
		 * password = user.getUserAccount().getPassword();
		 * hash = this.encoder.encodePassword(password, null);
		 * user.getUserAccount().setPassword(hash);
		 */
		this.userRepository.save(user);
	}

	public Collection<User> findAll() {
		Collection<User> result;

		result = this.userRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public User findOne(final int userId) {
		User result;

		result = this.userRepository.findOne(userId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods -----------------------------------------------

	public User reconstruct(final RegistrationForm registrationForm, final BindingResult binding) {
		User result;
		UserAccount userAccount;

		result = this.create();
		result.setName(registrationForm.getName());
		result.setSurname(registrationForm.getSurname());
		result.setEmail(registrationForm.getEmail());
		result.setPhoneNumber(registrationForm.getPhoneNumber());
		result.setPostalAddress(registrationForm.getPostalAddress());
		result.setBirthdate(registrationForm.getBirthdate());

		userAccount = result.getUserAccount();
		userAccount.setUsername(registrationForm.getUsername());
		userAccount.setPassword(registrationForm.getPassword());

		// TODO: comentamos esto de prueba
		//this.validator.validate(result, binding);

		return result;
	}

	protected void addRendezvous(final User user, final Rendezvous rendezvous) {
		Collection<Rendezvous> aux;

		aux = new HashSet<>(user.getCreatedRendezvouses());
		aux.add(rendezvous);
		user.setCreatedRendezvouses(aux);
	}

}
