package services;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.joda.time.Years;
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
		LocalDate birthdate, currentDate;
		Years age;
		
		currentDate = new LocalDate();
		birthdate = LocalDate.fromDateFields(actor.getBirthdate());
		age = Years.yearsBetween(birthdate, currentDate);
		
		result = age.getYears();
		
		return result;
	}
	
}
