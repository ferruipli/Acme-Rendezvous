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
		this.checkByPrincipal(rendezvous);
		
		Rendezvous result;
		
		if (rendezvous.getId() != 0) {
			this.checkFinalMode(rendezvous);
		}
		
		result = this.rendezvousRepository.save(rendezvous);
		
		return result;
	}
	
	public void delete(Rendezvous rendezvous) {
		this.checkByPrincipal(rendezvous);
		Assert.isTrue(rendezvous.getId() != 0);
		this.checkFinalMode(rendezvous);
		
		rendezvous.setFinalMode(true);
		rendezvous.setIsFlagged(true);
		
	}
	
	
	// Other business methods -----------------------------------------------
	
	public void reserve(Rendezvous rendezvous) {
		
	}
	
	public void remove(Rendezvous rendezvous) {
		Assert.notNull(rendezvous);
		Assert.isTrue(rendezvous.getId() != 0);
		
		this.rendezvousRepository.delete(rendezvous);
	}
	
	public void cancel(Rendezvous rendezvous) {
		
	}
	
	public void checkByPrincipal(Rendezvous rendezvous) {
		Assert.notNull(rendezvous);
		
		User user;
		
		user = (User)this.actorService.findByPrincipal();
		
		Assert.isTrue(rendezvous.getCreator().equals(user));
	}
	
	private void checkAdult() {
		Actor actor;
		long edad;
		
		actor = this.actorService.findByPrincipal();
		edad = this.actorService.getEdad(actor);
		
		Assert.isTrue(edad>=18);
	}
	
	private void checkFinalMode(Rendezvous rendezvous) {
		Assert.isTrue(rendezvous.getFinalMode() == false);
	}
	
	protected void addAttendant(Rendezvous rendezvous, User attendant) {
		Collection<User> aux;
		
		aux = new HashSet<>(rendezvous.getAttendants());
		aux.add(attendant);
		rendezvous.setAttendants(aux);
	}
	
	protected void addComment(Rendezvous rendezvous, Comment comment) {
		Collection<Comment> aux;
		
		aux = new HashSet<>(rendezvous.getComments());
		aux.add(comment);
		rendezvous.setComments(aux);
	}
	
	protected void removeComment(Rendezvous rendezvous, Comment comment) {
		Collection<Comment> aux;
		
		aux = new HashSet<>(rendezvous.getComments());
		aux.remove(comment);
		rendezvous.setComments(aux);
	}
	
	protected void addSimilarOnes(Rendezvous rendezvous, Rendezvous similarOne) {
		Collection<Rendezvous> aux;
		
		aux = new HashSet<>(rendezvous.getSimilarOnes());
		aux.add(similarOne);
		rendezvous.setSimilarOnes(aux);
	}
	
	protected void removeSimilarOnes(Rendezvous rendezvous, Rendezvous similarOne) {
		Collection<Rendezvous> aux;
		
		aux = new HashSet<>(rendezvous.getSimilarOnes());
		aux.remove(rendezvous);
		rendezvous.setSimilarOnes(aux);
	}
	
	protected void addAnnouncement(Rendezvous rendezvous, Announcement announcement) {
		Collection<Announcement> aux;
		
		aux = new HashSet<>(rendezvous.getAnnouncements());
		aux.add(announcement);
		rendezvous.setAnnouncements(aux);
	}
	
	protected void removeAnnouncement(Rendezvous rendezvous, Announcement announcement) {
		Collection<Announcement> aux;
		
		aux = new HashSet<>(rendezvous.getAnnouncements());
		aux.remove(announcement);
		rendezvous.setAnnouncements(aux);
	}
	
}
