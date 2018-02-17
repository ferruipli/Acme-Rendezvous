package services;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Announcement;
import domain.Comment;
import domain.Rendezvous;
import domain.User;

import repositories.RendezvousRepository;

@Service
@Transactional
public class RendezvousService {

	// Managed repository ---------------------------------------------------
	@Autowired
	private RendezvousRepository rendezvousRepository;
	
	// Supporting services --------------------------------------------------
	@Autowired
	private ActorService actorService;
	
	// Constructors ---------------------------------------------------------
	public RendezvousService() {
		super();
	}
	
	// CRUD methods ---------------------------------------------------------
	public Rendezvous findOne(int rendezvousId) {
		Assert.notNull(rendezvousId != 0);
		
		Rendezvous result;
		
		result = this.rendezvousRepository.findOne(rendezvousId);
		
		if (result.getAdultOnly() == true) {
			this.checkAdult();
		}
		
		return result;
	}
	
	public Collection<Rendezvous> findAll() {
		Collection<Rendezvous> results;
		
		results = this.rendezvousRepository.findAll();
		
		return results;
	}
	
	public Rendezvous create() {
		Rendezvous result;
		Collection<User> attendants;
		User user;
		
		user = (User)this.actorService.findByPrincipal();
		
		attendants = new HashSet<User>();
		attendants.add(user);
		
		result = new Rendezvous();
		result.setAnnouncements(Collections.<Announcement>emptySet());
		result.setAttendants(attendants);
		result.setComments(Collections.<Comment>emptySet());
		result.setCreator(user);
		result.setSimilarOnes(Collections.<Rendezvous>emptySet());
		
		return result;
	}
	
	public Rendezvous save(Rendezvous rendezvous) {
		return null;
	}
	
	public void delete(Rendezvous rendezvous) {
		
	}
	
	
	// Other business methods -----------------------------------------------
	
	public void reserve(Rendezvous rendezvous) {
		
	}
	
	public void remove(Rendezvous rendezvous) {
		
	}
	
	public void checkByPrincipal(Rendezvous rendezvous) {
		
	}
	
	private void checkAdult() {
		Actor actor;
		long edad;
		
		actor = this.actorService.findByPrincipal();
		edad = this.actorService.getEdad(actor);
		
		Assert.isTrue(edad>=18);
	}
	
}
