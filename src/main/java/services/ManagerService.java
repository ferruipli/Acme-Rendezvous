
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ManagerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Manager;
import domain.Services;

@Service
@Transactional
public class ManagerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ManagerRepository	managerRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ManagerService() {
		super();
	}

	// CRUD Services ----------------------------------------------------------

	// Other business methods -------------------------------------------------

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

}
