package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	

}
