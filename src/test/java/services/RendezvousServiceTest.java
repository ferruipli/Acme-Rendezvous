package services;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Announcement;
import domain.Comment;
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
	
	// Other services ------------------------------------------

	// Test ----------------------------------------------------
	/**
	 * Acme Rendezvous:
	 * Req. 4.2 : An actor who is not authenticated must be able to 
	 * list the rendezvouses in the system 
	 */
	@Test
	public void testListRendezvouses(){
		this.rendezvousService.findAll();
	}
	
	/**
	 * Acme Rendezvous:
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
		rendezvous.setReserves(Collections.<RSVP> emptySet());
		rendezvous.setComments(Collections.<Comment> emptySet());
		rendezvous.setSimilarOnes(Collections.<Rendezvous> emptySet());
		rendezvous.setAnnouncements(Collections.<Announcement> emptySet());
		rendezvous.setQuestions(Collections.<Question> emptySet());
		this.rendezvousService.save(rendezvous);
		
		super.unauthenticate();
	}
	
	/**
	 * Acme Rendezvous:
	 * An actor who is authenticated by user must b
	 * Create a rendezvous, which he's implicitly assumed to attend. 
	 * Note that a user may edit his or her rendezvouses as long as they aren't saved them in final mode. 
	 * Once a rendezvous is saved in final mode, it cannot be edited or deleted by the creator.
	 * REMARK: user authenticated
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testUnauthenticatedCreateRendezvous(){
		
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
		rendezvous.setReserves(Collections.<RSVP> emptySet());
		rendezvous.setComments(Collections.<Comment> emptySet());
		rendezvous.setSimilarOnes(Collections.<Rendezvous> emptySet());
		rendezvous.setAnnouncements(Collections.<Announcement> emptySet());
		rendezvous.setQuestions(Collections.<Question> emptySet());
		this.rendezvousService.save(rendezvous);
		
	}
	
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
		rendezvous.setReserves(Collections.<RSVP> emptySet());
		rendezvous.setComments(Collections.<Comment> emptySet());
		rendezvous.setSimilarOnes(Collections.<Rendezvous> emptySet());
		rendezvous.setAnnouncements(Collections.<Announcement> emptySet());
		rendezvous.setQuestions(Collections.<Question> emptySet());
		this.rendezvousService.save(rendezvous);
		
		super.unauthenticate();
	}
}
