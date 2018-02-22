package services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RSVPRepository;
import domain.Answer;
import domain.RSVP;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class RSVPService {
	
	// Managed repository --------------------------------------------------------------------
	
	@Autowired
	private RSVPRepository rsvpRepository;
	
	// Supporting services ------------------------------------------------------------------
	
	@Autowired
	private RendezvousService rendezvousService;
	
	@Autowired
	private ActorService actorService;

	// Constructors ---------------------------------------------------------
	
	public RSVPService(){
		super();
	}
	
	// CRUD methods ---------------------------------------------------------
	
	public RSVP create(Rendezvous rendezvous){
		RSVP result;
		User user;
		
		result = new RSVP();
		user = (User) this.actorService.findByPrincipal();
		
		result.setAnswers(Collections.<Answer> emptySet());
		result.setUser(user);
		result.setRendezvous(rendezvous);
		
		return result;
		
	}
	
	public RSVP findOne(int rsvpId){
		Assert.notNull(rsvpId != 0);
		
		RSVP result;
		
		result = this.rsvpRepository.findOne(rsvpId);
		
		return result;
	}
	
	public Collection<RSVP> findAll(){
		Collection<RSVP> results;
		
		results = this.rsvpRepository.findAll();
		
		return results;
	}
	
	public RSVP save(RSVP rsvp){
		Assert.notNull(rsvp);
		RSVP result;
		Date date;
		User user;
		long edad;
		
		user = (User) this.actorService.findByPrincipal();
		edad = this.actorService.getEdad(user);
		
		Assert.isTrue(edad>=18);
		date = new Date(System.currentTimeMillis());
		
		Assert.isTrue(rsvp.getRendezvous().getMoment().after(date));
		
		this.rendezvousService.addAttendant(rsvp.getRendezvous(), user);
		result = this.rsvpRepository.save(rsvp);
			
		return result;
	}
	
	
	
	// Other business methods ------------------------------------------------------------
	
	public void cancel(RSVP rsvp){
		Assert.isTrue(rsvp.getId() !=0);
		User user;
		
		user = (User) this.actorService.findByPrincipal();
		
		this.rendezvousService.removeAttendant(rsvp.getRendezvous(), user);
		this.rsvpRepository.delete(rsvp);
		
		
	}
	


}
