package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Answer;
import domain.Question;
import domain.RSVP;
import domain.Rendezvous;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class RendezvousServiceTest extends AbstractTest {

	// Service under testing -----------------------------------
	@Autowired
	private RendezvousService	rendezvousService;
	
	@Autowired
	private UserService			userService;
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private RSVPService			rsvpService;
	
	// Other services ------------------------------------------

	// Test ----------------------------------------------------
	
	/*
	 * Requirement 20.1: Display information about the users who have
	 * RSVPd a rendezvous, which, in turn, must show their answers
	 * to the questions that the creator has registered.
	 */
	
	/*
	 * Se pretende comprobar con este caso de test que un usuario no
	 * autenticado puede visualizar las cuestiones y respuestas de una cita
	 * concreta
	 */
	
	@Test
	public void testShowAnswersOfQuestionsFromRendezvous() {
		super.authenticate(null);
		
		int rendezvousId;
		Collection<Question> questions;
		Collection<Answer> answers;
		
		rendezvousId = super.getEntityId("rendezvous1");
		
		answers = this.answerService.findOrderedAnswersByRendezvousId(rendezvousId);
		questions = this.rendezvousService.findOrderedQuestionsByRendezvousId(rendezvousId);
		
		Assert.notNull(answers);
		Assert.notNull(questions);
		
		Assert.isTrue(answers.size() > 0);
		Assert.isTrue(questions.size() > 0);
		
		super.authenticate(null);
	}
	
	/**
	 * Req. 1.4.2 : An actor who is not authenticated must be able to 
	 * list the rendezvouses in the system 
	 */
	@Test
	public void testListRendezvouses(){
		this.rendezvousService.findAll();
	}
	
	/**
	 * Req 1.5.2
	 * An actor who is authenticated by user must b
	 * Create a rendezvous, which he's implicitly assumed to attend. 
	 * Note that a user may edit his or her rendezvouses as long as they aren't saved them in final mode. 
	 * Once a rendezvous is saved in final mode, it cannot be edited or deleted by the creator.
	 * REMARK: user authenticated
	 */
	@Test
	public void testCreateRendezvous(){
		super.authenticate("user1");
		
		Rendezvous rendezvous;
		Calendar c;
		Date moment;
		
		c = Calendar.getInstance();
		c.set(2018, 04, 25, 20, 00);
		moment = c.getTime();
		
		rendezvous = this.rendezvousService.create();
		rendezvous.setName("rendezvous");
		rendezvous.setDescription("description");
		rendezvous.setMoment(moment);		
		this.rendezvousService.save(rendezvous);
		
		super.unauthenticate();
	}
	
	/**
	 * Req 1.5.2
	 * An actor who is authenticated by user must be able to:
	 * Create a rendezvous, which he's implicitly assumed to attend. 
	 * Note that a user may edit his or her rendezvouses as long as they aren't saved them in final mode. 
	 * Once a rendezvous is saved in final mode, it cannot be edited or deleted by the creator.
	 * REMARK: user authenticated
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testUnauthenticatedCreateRendezvous(){
		
		Rendezvous rendezvous;
		Calendar c;
		Date moment;
		
		c = Calendar.getInstance();
		c.set(2018, 04, 25, 20, 00);
		moment = c.getTime();
		
		rendezvous = this.rendezvousService.create();
		
		rendezvous.setName("rendezvous");
		rendezvous.setDescription("description");
		rendezvous.setMoment(moment);
		this.rendezvousService.save(rendezvous);
		
	}
	/**
	 * Req 1.5.2
	 * An actor who is authenticated by user must be able to:
	 * Create a rendezvous, which he's implicitly assumed to attend. 
	 * Note that a user may edit his or her rendezvouses as long as they aren't saved them in final mode. 
	 * Once a rendezvous is saved in final mode, it cannot be edited or deleted by the creator.
	 * REMARK: the actor isn't a user
	 */
	@Test(expected = ClassCastException.class)
	public void testManagerCreateRendezvous(){
		
		super.authenticate("manager1");
		Rendezvous rendezvous;
		User creator;
		Calendar c;
		Date moment;
		
		c = Calendar.getInstance();
		c.set(2018, 04, 25, 20, 00);
		moment = c.getTime();
		creator = this.userService.findOne(super.getEntityId("user1"));
		rendezvous = this.rendezvousService.create();
		
		rendezvous.setName("rendezvous");
		rendezvous.setDescription("description");
		rendezvous.setMoment(moment);
		rendezvous.setCreator(creator);
		this.rendezvousService.save(rendezvous);
		
		super.unauthenticate();
	}
	
	/**
	 * Req 1.5.3
	 * An actor who is authenticated by user must be able to:
	 * Update the rendezvouses that he or she's created.
	 * REMARK: user with own rendezvous
	 */
	@Test
	public void testUpdateOwnRendezvous(){
		super.authenticate("user2");
		Rendezvous rendezvous;
		Calendar c;
		Date moment;
		
		c = Calendar.getInstance();
		c.set(2018, 04, 25, 20, 00);
		moment = c.getTime();
		
		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		rendezvous.setMoment(moment);
		rendezvous.setFinalMode(false);
		this.rendezvousService.save(rendezvous);
		
		super.unauthenticate();
	}
	
	
	/**
	 * Req 1.5.3
	 * An actor who is authenticated by user must be able to:
	 * Update the rendezvouses that he or she's created.
	 * REMARK: user with not own rendezvous
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testUpdateNotOwnRendezvous(){
		super.authenticate("user2");
		Rendezvous rendezvous;
		Calendar c;
		Date moment;
		
		c = Calendar.getInstance();
		c.set(2018, 04, 25, 20, 00);
		moment = c.getTime();
		
		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous4"));
		rendezvous.setMoment(moment);
		rendezvous.setFinalMode(false);
		this.rendezvousService.save(rendezvous);
		
		super.unauthenticate();
	}
	/**
	 * Req 1.5.3 
	 * An actor who is authenticated by user must be able to:
	 * Delete the rendezvouses that he or she's created. 
	 * Deletion is virtual, that is: the information is not removed from the database, 
	 * but the rendezvous cannot be updated. 
	 * Deleted rendezvouses are flagged as such when they are displayed.
	 * REMARK: user with own rendezvous
	 */
	@Test
	public void testDeleteOwnRendezvous(){
		super.authenticate("user2");
		
		Rendezvous rendezvous;
		
		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous2"));
		this.rendezvousService.delete(rendezvous);
		
		super.unauthenticate();
	}
	
	/**
	 * Req 1.5.3 
	 * An actor who is authenticated by user must be able to:
	 * Delete the rendezvouses that he or she's created. 
	 * Deletion is virtual, that is: the information is not removed from the database, 
	 * but the rendezvous cannot be updated. 
	 * Deleted rendezvouses are flagged as such when they are displayed.
	 * REMARK: user with not own rendezvous
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteNotOwnRendezvous(){
		super.authenticate("user1");
		
		Rendezvous rendezvous;
		
		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous4"));
		this.rendezvousService.delete(rendezvous);
		
		super.unauthenticate();
	}
	/**
	 * Req 1.5.3 
	 * An actor who is authenticated by user must be able to:
	 * Delete the rendezvouses that he or she's created. 
	 * Deletion is virtual, that is: the information is not removed from the database, 
	 * but the rendezvous cannot be updated. 
	 * Deleted rendezvouses are flagged as such when they are displayed.
	 * REMARK: user with own rendezvous but its final mode = true
	 */
	
	@Test(expected=IllegalArgumentException.class)
	public void testDeleteOwnRendezvousFinalModeTrue(){
		super.authenticate("user2");
		
		Rendezvous rendezvous;
		
		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		this.rendezvousService.delete(rendezvous);
		
		super.unauthenticate();
	}
	
	/**
	 * Req 1.5.5
	 * List the rendezvouses that he or she's RSVPd.
	 */
	@Test
	public void testListRendezvousesRSVPByUser(){
		super.authenticate("user2");
		
		this.rendezvousService.findRendezvousesRSVPByUserId(super.getEntityId("user2"));
		
		super.unauthenticate();
	}
	
	/**
	 * Req 1.6.2
	 * An actor who is authenticated as an administrator must be able to:
	 * Remove a rendezvous that he or she thinks is inappropriate.
	 */
	@Test
	public void testRemoveRendezvousInappropriate(){
		super.authenticate("admin");
		Rendezvous rendezvous;
		
		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		this.rendezvousService.remove(rendezvous);
		
		super.unauthenticate();
	}
	
	/**
	 * Req 1.14
	 * Some rendezvouses may be flagged as "adult only", in which case every attempt to RSVP them
	 * by users who are under 18 must be prohibited. Such rendezvouses must not be displayed
	 * unless the user who is browsing them is at least 18 year old. Obviously, they must not be
	 * shown to unauthenticated users.
	 */
	@Test
	public void testRSVPRendezvousAdultOnly(){

		Rendezvous rendezvous;
		RSVP rsvp;
		
		super.authenticate("user2"); // User mayor de edad
		
		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous5"));
		rendezvous.setFinalMode(false);
		rendezvous.setAdultOnly(true);
		this.rendezvousService.save(rendezvous);
				
		rsvp = this.rsvpService.create(rendezvous);
		this.rsvpService.save(rsvp);
		
		super.unauthenticate();
		
	}
	
	/**
	 * Req 1.14
	 * Some rendezvouses may be flagged as "adult only", in which case every attempt to RSVP them
	 * by users who are under 18 must be prohibited. Such rendezvouses must not be displayed
	 * unless the user who is browsing them is at least 18 year old. Obviously, they must not be
	 * shown to unauthenticated users.
	 * REMARK: menor de edad intenta reservar un rendezvous con adultOnly = true;
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testRSVPRendezvousAdultOnly2(){
		
		Rendezvous rendezvous;
		RSVP rsvp;
		
		super.authenticate("user2"); // User mayor de edad (creador del rendezvous 5)
		
		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous5"));
		rendezvous.setAdultOnly(true);
		this.rendezvousService.save(rendezvous);
		
		super.unauthenticate();
		
		super.authenticate("user3"); // User menor de edad
				
		rsvp = this.rsvpService.create(rendezvous);
		this.rsvpService.save(rsvp);
		
		super.unauthenticate();
	}
	/**
	 * Req 1.16.4
	 * Link one of the rendezvouses that he or she's created to other similar rendezvouses.
	 * Test positivo
	 */
	@Test
	public void testLinkSimilarRendezvous(){
		
		Rendezvous rendezvous;
		int rendezvous2Id;
		Rendezvous rendezvous2;
		Collection<Rendezvous> similarOnes;
		Calendar c;
		Date moment;
		
		c= Calendar.getInstance();
		c.set(2019, 04, 24, 11, 00);
		moment = c.getTime();
		
		super.authenticate("user2");
		
		rendezvous = this.rendezvousService.create();
		similarOnes = rendezvous.getSimilarOnes();
		similarOnes = new HashSet<Rendezvous>(similarOnes);
		rendezvous2Id = super.getEntityId("rendezvous5");
		rendezvous2 = this.rendezvousService.findOne(rendezvous2Id);
		rendezvous.setMoment(moment);
		similarOnes.add(rendezvous2);
		
		this.rendezvousService.save(rendezvous);
		
		super.unauthenticate();
	}
	
	/**
	 * Req 1.16.4
	 * Link one of the rendezvouses that he or she's created to other similar rendezvouses.
	 * REMARK: un manager intenta añadir un rendezvous a los similarOnes creados por un usuario
	 */
	@Test(expected=ClassCastException.class)
	public void testLinkSimilarRendezvous2(){
		
		Rendezvous rendezvous;
		int rendezvous2Id;
		Rendezvous rendezvous2;
		Collection<Rendezvous> similarOnes;
		Calendar c;
		Date moment;
		
		c= Calendar.getInstance();
		c.set(2019, 04, 24, 11, 00);
		moment = c.getTime();
		
		super.authenticate("user2");
		
		rendezvous = this.rendezvousService.create();
		similarOnes = rendezvous.getSimilarOnes();
		similarOnes = new HashSet<Rendezvous>(similarOnes);
		rendezvous2Id = super.getEntityId("rendezvous4"); // añadir a similarOnes un rendezvous no creado por el
		rendezvous2 = this.rendezvousService.findOne(rendezvous2Id);
		rendezvous.setMoment(moment);
		
		super.unauthenticate();
		
		super.authenticate("manager1");
		similarOnes.add(rendezvous2);
		this.rendezvousService.save(rendezvous);
		super.unauthenticate();
	}
	
}
