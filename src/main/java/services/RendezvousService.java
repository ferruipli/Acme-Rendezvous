
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
import forms.RendezvousForm;

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
	private RSVPService				rsvpService;

	@Autowired
	private AnnouncementService		announcementService;

	@Autowired
	private QuestionService			questionService;


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

		gpsCoordinates = new GPS();
		gpsCoordinates.setLatitude(0.0);
		gpsCoordinates.setLongitude(0.0);
		
		user = (User)this.actorService.findByPrincipal();
		
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
		
		boolean res;
		
		res = true;

		rendezvous.setFinalMode(res);
		rendezvous.setIsFlagged(res);
	}


	// Other business methods -----------------------------------------------

	@Autowired
	private Validator	validator;


	public Rendezvous reconstruct(final RendezvousForm rendezvousForm, final BindingResult binding) {
		Rendezvous result;
		GPS gpsCoordinates;

		if (rendezvousForm.getId() == 0)
			result = this.create();
		else {
			result = this.rendezvousRepository.findOne(rendezvousForm.getId());
			result.setName(rendezvousForm.getName());
			result.setDescription(rendezvousForm.getDescription());
			result.setMoment(rendezvousForm.getMoment());

			gpsCoordinates = result.getGpsCoordinates();
			gpsCoordinates.setLatitude(rendezvousForm.getGpsCoordinates().getLatitude());
			gpsCoordinates.setLongitude(rendezvousForm.getGpsCoordinates().getLongitude());

			result.setFinalMode(rendezvousForm.isFinalMode());
			result.setAdultOnly(rendezvousForm.isAdultOnly());
			result.setUrlPicture(rendezvousForm.getUrlPicture());
			result.setSimilarOnes(rendezvousForm.getSimilarOnes());
			result.setGpsCoordinates(gpsCoordinates);

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

		Collection<Rendezvous> rendezvouses;
		Collection<RSVP> RSVPs;
		Collection<Question> questions;
		Collection<Announcement> announcements;

		rendezvouses = rendezvous.getSimilarOnes();
		RSVPs = rendezvous.getReserves();
		questions = rendezvous.getQuestions();
		announcements = rendezvous.getAnnouncements();

		//Removing all the RSVP relates with this rendezvous
		if (RSVPs != null && !RSVPs.isEmpty())
			for (final RSVP rs : RSVPs)
				this.rsvpService.removeByAdmin(rs);

		// Removing all the announcements relates with this rendezvous
		if (announcements != null && !announcements.isEmpty())
			for (final Announcement a : announcements)
				this.announcementService.removeByAdmin(a);

		// Removing all the questions relates with this rendezvous
		if (questions != null && !questions.isEmpty())
			for (final Question q : questions)
				this.questionService.delete(q);

		// Update User::createdRendezvouses
		this.userService.removeRendezvous(rendezvous.getCreator(), rendezvous);

		// Update Rendezvous::similarOnes
		if (rendezvouses != null && !rendezvouses.isEmpty())
			for (final Rendezvous r : rendezvouses)
				this.removeSimilarOnes(r, rendezvous);

		//if (rendezvous.getGpsCoordinates() != null)
			//this.gpsService.delete(rendezvous.getGpsCoordinates());

		this.rendezvousRepository.delete(rendezvous);

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
		aux.remove(similarOne);
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

	protected void addQuestion(final Rendezvous rendezvous, final Question question) {
		Collection<Question> aux;

		aux = new HashSet<>(rendezvous.getQuestions());
		aux.add(question);
		rendezvous.setQuestions(aux);
	}

	protected void removeQuestion(final Rendezvous rendezvous, final Question question) {
		Collection<Question> aux;

		aux = new HashSet<>(rendezvous.getQuestions());
		aux.remove(question);
		rendezvous.setQuestions(aux);
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

	public Collection<Question> findOrderedQuestionsByRendezvousId(final int rendezvousId) {
		Collection<Question> result;

		result = this.rendezvousRepository.findOrderedQuestionsByRendezvousId(rendezvousId);
		Assert.notNull(result);

		return result;
	}
}
