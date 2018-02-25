
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class UserService {

	// Managed repository ---------------------------------------------------

	@Autowired
	private UserRepository	userRepository;


	// Supporting services --------------------------------------------------

	// Constructors ---------------------------------------------------------

	public UserService() {
		super();
	}

	// CRUD methods ---------------------------------------------------------

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

	protected void addRendezvous(final User user, final Rendezvous rendezvous) {
		Collection<Rendezvous> aux;

		aux = new HashSet<>(user.getCreatedRendezvouses());
		aux.add(rendezvous);
		user.setCreatedRendezvouses(aux);
	}

	protected void removeRendezvous(User user, Rendezvous rendezvous) {
		Collection<Rendezvous> aux;
		
		aux = new HashSet<>(user.getCreatedRendezvouses());
		aux.remove(rendezvous);
		user.setCreatedRendezvouses(aux);
	}
	
	public User findByPrincipal() {
		User res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findUserByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;
	}
	public User findUserByUserAccount(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		User result;

		result = this.userRepository.findUserByUserAccount(userAccountId);

		Assert.notNull(result);

		return result;
	}

}
