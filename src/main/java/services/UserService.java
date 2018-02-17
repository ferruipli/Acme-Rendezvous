package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;
import domain.User;

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
	
	public User findByPrincipal(){
		User result;
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		result = this.findUserByUserAccount(userAccount.getId());
		
		return result;
	}
	
	public User findUserByUserAccount(int userAccountId) {
		User result;
		
		result = this.userRepository.getUserByUserAccount(userAccountId);
		Assert.notNull(result);
		
		return result;
	}

}
