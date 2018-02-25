
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RendezvousRepository;
import domain.Actor;
import domain.Announcement;
import domain.Comment;
import domain.GPS;
import domain.Question;
import domain.RSVP;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class RendezvousService {

	// Managed repository ---------------------------------------------------
	@Autowired
	private RendezvousRepository	rendezvousRepository;

	// Supporting services --------------------------------------------------
	@Autowired
	private ActorService			actorService;

	@Autowired
	private UserService				userService;

	@Autowired
	private GPSService				gpsService;


	// Constructors ---------------------------------------------------------
	public RendezvousService() {
		super();
	}

	// CRUD methods ---------------------------------------------------------
	public Rendezvous findOne(final int rendezvousId) {
		Assert.notNull(rendezvousId != 0);

		Rendezvous result;

		result = this.rendezvousRepository.findOne(rendezvousId);

		if (result.getAdultOnly() == true)
			this.checkAdult();

		return result;
	}

	public Collection<Rendezvous> findAll() {
		Collection<Rendezvous> results;

		results = this.rendezvousRepository.findAll();

		return results;
	}

	public Rendezvous create() {
		Rendezvous result;
		User user;
		GPS gpsCoordinates;

		user = (User) this.actorService.findByPrincipal();
		gpsCoordinates = this.gpsService.create();

		result = new Rendezvous();
		result.setAnnouncements(Collections.<Announcement> emptySet());
		result.setAttendants(Collections.<User> emptySet());
		result.setComments(Collections.<Comment> emptySet());
		result.setCreator(user);
		result.setGpsCoordinates(gpsCoordinates);
		result.setSimilarOnes(Collections.<Rendezvous> emptySet());
		result.setQuestions(Collections.<Question> emptySet());
		result.setReserves(Collections.<RSVP> emptySet());

		return result;
	}

	public Rendezvous save(final Rendezvous rendezvous) {
		this.checkByPrincipal(rendezvous);
		this.checkMoment(rendezvous.getMoment());

		Rendezvous result;

		if (rendezvous.getId() != 0)
			this.checkFinalMode(rendezvous);

		result = this.rendezvousRepository.save(rendezvous);

		if (rendezvous.getId() == 0)
			// Updates User::createdRendezvous of creator
			this.userService.addRendezvous(rendezvous.getCreator(), result);

		return result;
	}

	public void delete(final Rendezvous rendezvous) {
		this.checkByPrincipal(rendezvous);
		Assert.isTrue(rendezvous.getId() != 0);
		this.checkFinalMode(rendezvous);

		rendezvous.setFinalMode(true);
		rendezvous.setIsFlagged(true);

	}


	// Other business methods -----------------------------------------------
	
	@Autowired
	private Validator	validator;

	public Rendezvous reconstruct(final Rendezvous rendezvous, final BindingResult binding) {
		Rendezvous result;

		if (rendezvous.getId() == 0)
			result = rendezvous;
		else {
			result = this.rendezvousRepository.findOne(rendezvous.getId());
			result.setName(rendezvous.getName());
			result.setDescription(rendezvous.getDescription());
			result.setMoment(rendezvous.getMoment());
			result.setGpsCoordinates(rendezvous.getGpsCoordinates());
			result.setFinalMode(rendezvous.getFinalMode());
			result.setAdultOnly(rendezvous.getAdultOnly());
			result.setUrlPicture(rendezvous.getUrlPicture());
			result.setSimilarOnes(rendezvous.getSimilarOnes());

			this.validator.validate(result, binding);
		}

		return result;
	}

	public Collection<Rendezvous> findAllAvailable() {
		Collection<Rendezvous> results;

		results = this.rendezvousRepository.findAllAvailable();

		return results;
	}

	public void reserve(final int rendezvousId) {
		Assert.isTrue(rendezvousId != 0);

		User user;
		Rendezvous rendezvous;

		user = (User) this.actorService.findByPrincipal();
		rendezvous = this.rendezvousRepository.findOne(rendezvousId);

		this.addAttendant(rendezvous, user);
	}

	public void remove(final Rendezvous rendezvous) {
		Assert.isTrue(rendezvous.getId() != 0);

		if (rendezvous.getGpsCoordinates() != null)
			this.gpsService.delete(rendezvous.getGpsCoordinates());

		this.rendezvousRepository.delete(rendezvous);

		// Update User::createdRendezvouses
		this.userService.removeRendezvous(rendezvous.getCreator(), rendezvous);
	}

	public void cancel(final Rendezvous rendezvous) {
		Assert.notNull(rendezvous);
		User user;

		user = (User) this.actorService.findByPrincipal();

		this.removeAttendant(rendezvous, user);
	}

	private void checkMoment(final Date date) {
		Date currentMoment;

		currentMoment = new Date();

		Assert.isTrue(date.after(currentMoment));
	}

	private void checkByPrincipal(final Rendezvous rendezvous) {
		Assert.notNull(rendezvous);

		User user;

		user = (User) this.actorService.findByPrincipal();

		Assert.isTrue(rendezvous.getCreator().equals(user));
	}

	private void checkAdult() {
		Actor actor;
		int edad;

		actor = this.actorService.findByPrincipal();
		edad = this.actorService.getEdad(actor);

		Assert.isTrue(edad >= 18);
	}

	private void checkFinalMode(final Rendezvous rendezvous) {
		Assert.isTrue(rendezvous.getFinalMode() == false);
	}

	protected void addAttendant(final Rendezvous rendezvous, final User attendant) {
		Collection<User> aux;

		aux = new HashSet<>(rendezvous.getAttendants());
		aux.add(attendant);
		rendezvous.setAttendants(aux);
	}

	protected void removeAttendant(final Rendezvous rendezvous, final User attendant) {
		Collection<User> aux;

		aux = new HashSet<>(rendezvous.getAttendants());
		aux.remove(attendant);
		rendezvous.setAttendants(aux);
	}

		public void addComment(final Rendezvous rendezvous, final Comment comment) {
		User user;
		user = (User) this.actorService.findByPrincipal();
		Assert.isTrue(rendezvous.getAttendants().contains(user));
		Collection<Comment> aux;

		aux = new HashSet<>(rendezvous.getComments());
		aux.add(comment);
		rendezvous.setComments(aux);
	}

		public void removeComment(final Rendezvous rendezvous, final Comment comment) {
		Collection<Comment> aux;

		aux = new HashSet<>(rendezvous.getComments());
		aux.remove(comment);
		rendezvous.setComments(aux);
	}

	protected void addSimilarOnes(final Rendezvous rendezvous, final Rendezvous similarOne) {
		Collection<Rendezvous> aux;

		aux = new HashSet<>(rendezvous.getSimilarOnes());
		aux.add(similarOne);
		rendezvous.setSimilarOnes(aux);
	}

	protected void removeSimilarOnes(final Rendezvous rendezvous, final Rendezvous similarOne) {
		Collection<Rendezvous> aux;

		aux = new HashSet<>(rendezvous.getSimilarOnes());
		aux.remove(rendezvous);
		rendezvous.setSimilarOnes(aux);
	}

	public void addAnnouncement(final Rendezvous rendezvous, final Announcement announcement) {
		Collection<Announcement> aux;

		aux = new HashSet<>(rendezvous.getAnnouncements());
		aux.add(announcement);
		rendezvous.setAnnouncements(aux);
	}

	protected void removeAnnouncement(final Rendezvous rendezvous, final Announcement announcement) {
		Collection<Announcement> aux;

		aux = new HashSet<>(rendezvous.getAnnouncements());
		aux.remove(announcement);
		rendezvous.setAnnouncements(aux);
	}

	public Double[] avgSqrtRendezvousesPerUser() {
		Double[] result;

		result = this.rendezvousRepository.avgSqrtRendezvousesPerUser();

		return result;
	}

	public Double ratioOfUsersWithRendezvousVsUsersWithoutRendezvous() {
		Double result;

		result = this.rendezvousRepository.ratioOfUsersWithRendezvousVsUsersWithoutRendezvous();

		return result;
	}

	public Double[] avgSqrtUsersPerRendezvous() {
		Double[] result;

		result = this.rendezvousRepository.avgSqrtUsersPerRendezvous();

		return result;
	}

	public Double[] avgSqrtRendezvousesRSVPdPerUser() {

		Double[] result;

		result = this.rendezvousRepository.avgSqrtRendezvousesRSVPdPerUser();

		return result;
	}

	public Collection<Rendezvous> top10RendezvousesRSVPd() {
		Collection<Rendezvous> result;
		Page<Rendezvous> allRendezvouses;
		Pageable pageable;

		pageable = new PageRequest(0, 10);
		allRendezvouses = this.rendezvousRepository.top10RendezvousesRSVPd(pageable);

		result = allRendezvouses.getContent();

		return result;
	}

	public Collection<Rendezvous> rendezvousesLinkedPlus10() {
		Collection<Rendezvous> result;

		result = this.rendezvousRepository.rendezvousesLinkedPlus10();

		return result;

	}

	public Rendezvous finRendezvousFromAComment(final int commentId) {
		Rendezvous result;

		result = this.rendezvousRepository.findRendezvousFromAComment(commentId);

		return result;
	}

	public Collection<Rendezvous> findRendezvousesRSVPByUserId(final int userId) {
		Collection<Rendezvous> result;

		result = this.rendezvousRepository.findRendezvousesRSVPByUserId(userId);

		return result;
	}

	public Rendezvous findRendezvousByAnnouncement(final int announcementId) {
		Rendezvous result;

		result = this.rendezvousRepository.findRendezvousByAnnouncement(announcementId);

		return result;
	}

}
