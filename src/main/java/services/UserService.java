package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
	
	// Managed repository ---------------------------------------------------
	
	@Autowired
	//private UserRepository userRepository;
	
	// Supporting services --------------------------------------------------
	
	// Constructors ---------------------------------------------------------
	
	public UserService(){
		super();
	}
	
	// CRUD methods ---------------------------------------------------------
	
	// Other business methods -----------------------------------------------
	
	

}