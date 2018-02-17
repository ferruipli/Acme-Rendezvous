package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ActorService {

	// Managed repository ---------------------------------------------------
	@Autowired
	private ActorRepository actorRepository;
	
	// Supporting services --------------------------------------------------
		
	// Constructors ---------------------------------------------------------
	public ActorService() {
		super();
	}
	
	// CRUD methods ---------------------------------------------------------
		
	// Other business methods -----------------------------------------------
	
	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		result = this.findActorByUserAccount(userAccount.getId());
		
		return result;
	}
	
	public Actor findActorByUserAccount(int userAccountId) {
		Actor result;
		
		result = this.actorRepository.getUserByUserAccount(userAccountId);
		Assert.notNull(result);
		
		return result;
	}
	
	public long getEdad(Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		
		long result;
		Date currentDate;
		
		currentDate = new Date();
		
		result = currentDate.getTime()-actor.getBirthdate().getTime();
		result = result/(365*24*3600*1000);
		
		return result;
	}
	
}
