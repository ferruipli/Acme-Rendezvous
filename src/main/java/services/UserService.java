
package services;

import java.util.Collection;
<<<<<<< HEAD
import java.util.HashSet;
=======
>>>>>>> ece392c03b50378ffd76c0697a99bc7f020846bc

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Rendezvous;
import domain.User;

import repositories.UserRepository;
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

	protected void addRendezvous(User user, Rendezvous rendezvous) {
		Collection<Rendezvous> aux;
		
		aux = new HashSet<>(user.getCreatedRendezvouses());
		aux.add(rendezvous);
		user.setCreatedRendezvouses(aux);
	}
	
}
