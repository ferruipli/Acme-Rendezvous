package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Rendezvous;
import domain.User;

import repositories.UserRepository;

@Service
@Transactional
public class UserService {
	
	// Managed repository ---------------------------------------------------
	
	@Autowired
	private UserRepository userRepository;
	
	// Supporting services --------------------------------------------------
	
	// Constructors ---------------------------------------------------------
	
	public UserService(){
		super();
	}
	
	// CRUD methods ---------------------------------------------------------
	
	// Other business methods -----------------------------------------------
	protected void addRendezvous(User user, Rendezvous rendezvous) {
		Collection<Rendezvous> aux;
		
		aux = new HashSet<>(user.getCreatedRendezvouses());
		aux.add(rendezvous);
		user.setCreatedRendezvouses(aux);
	}
	

}
