package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Answer;
import domain.RSVP;
import domain.Rendezvous;
import domain.User;

import repositories.RSVPRepository;

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
	
	public RSVP create(){
		RSVP result;
		User user;
		Collection<Answer> answers;
		Rendezvous rendezvous;
		
		result = new RSVP();
		user = (User) this.actorService.findByPrincipal();
		answers = new ArrayList<Answer>();
		rendezvous = new Rendezvous();
		
		
		result.setAnswers(answers);
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
		
		result = this.rsvpRepository.save(rsvp);
			
		return result;
	}
	
	public void delete(RSVP rsvp){
		Assert.isTrue(rsvp.getId() !=0);
		User user;
		
		user = (User) this.actorService.findByPrincipal();
		
		this.rendezvousService.removeAttendant(rsvp.getRendezvous(), user);
		this.rsvpRepository.delete(rsvp);
		
		
	}
	
	// Other business methods ------------------------------------------------------------
	


}
