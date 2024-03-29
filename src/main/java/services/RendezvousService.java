
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
	private RSVPService				rsvpService;

	@Autowired
	private AnnouncementService		announcementService;

	@Autowired
	private QuestionService			questionService;

	@Autowired
	private CommentService			commentService;


	// Constructors ---------------------------------------------------------
	public RendezvousService() {
		super();
	}

	// CRUD methods ---------------------------------------------------------
	public Rendezvous findOne(final int rendezvousId) {
		Assert.notNull(rendezvousId != 0);

		Rendezvous result;

		result = this.rendezvousRepository.findOne(rendezvousId);

		if (result.getAdultOnly() == true) {
			Assert.isTrue(this.actorService.findByPrincipal() != null);
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
		User user;
		GPS gpsCoordinates;

		gpsCoordinates = new GPS();
		gpsCoordinates.setLatitude(0.0);
		gpsCoordinates.setLongitude(0.0);

		user = (User) this.actorService.findByPrincipal();

		result = new Rendezvous();
		result.setAnnouncements(Collections.<Announcement> emptySet());
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

		Rendezvous result, old = null;

		if (rendezvous.getId() != 0) {
			old = this.rendezvousRepository.findOne(rendezvous.getId());
			this.checkFinalMode(old);
		}

		result = this.rendezvousRepository.save(rendezvous);

		if (rendezvous.getId() == 0)
			// Updates User::createdRendezvous of creator
			this.userService.addRendezvous(rendezvous.getCreator(), result);

		return result;
	}

	public void delete(final Rendezvous rendezvous) {
		this.checkByPrincipal(rendezvous);
		Assert.isTrue(rendezvous.getId() != 0);

		Rendezvous old;

		old = this.rendezvousRepository.findOne(rendezvous.getId());
		this.checkFinalMode(old);

		old.setFinalMode(true);
		old.setIsFlagged(true);
	}

	// Other business methods -----------------------------------------------

	public Collection<Rendezvous> findAllAvailable() {
		Collection<Rendezvous> results;
		User user;

		user = this.userService.findByPrincipal();

		if (!user.equals(null) && this.actorService.getEdad(user) >= 18)
			results = this.rendezvousRepository.findAllAvailable();
		else
			results = this.findAllAvailable2();

		return results;
	}

	public Collection<Rendezvous> findAllAvailable2() {
		Collection<Rendezvous> results;

		results = this.rendezvousRepository.findAllAvailable2();

		return results;
	}

	public void remove(final Rendezvous rendezvous) {
		Assert.isTrue(rendezvous.getId() != 0);

		Collection<Rendezvous> rendezvouses;
		Collection<RSVP> RSVPs;
		Collection<Question> questions;
		Collection<Announcement> announcements;
		Collection<Comment> comments;

		rendezvouses = rendezvous.getSimilarOnes();
		RSVPs = rendezvous.getReserves();
		questions = rendezvous.getQuestions();
		announcements = rendezvous.getAnnouncements();
		comments = rendezvous.getComments();

		// Removing all the comments relates with this rendezvous
		if (comments != null && !comments.isEmpty())
			for (final Comment c : comments)
				this.commentService.delete(c);

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

		this.rendezvousRepository.delete(rendezvous);
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

	protected void addAnnouncement(final Rendezvous rendezvous, final Announcement announcement) {
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

	public Rendezvous findRendezvousFromAComment(final int commentId) {
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

	public Integer findRendezvousByAnswerId(final int answerId) {
		Integer result;

		result = this.rendezvousRepository.findRendezvousIdByAnswerId(answerId);

		return result;
	}

	public Rendezvous findRendezvousByRSVPId(final int rsvpId) {
		Rendezvous result;

		result = this.rendezvousRepository.findRendezvousByRSVPId(rsvpId);

		return result;
	}
}
