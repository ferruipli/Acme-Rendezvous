package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Announcement;
import domain.Rendezvous;
import domain.User;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class AnnouncementServiceTest extends AbstractTest {
	
	// Service under testing -----------------------------------
	
	@Autowired
	private AnnouncementService announcementService;
	
	// Other services ------------------------------------------
	
	@Autowired
	private RendezvousService rendezvousService;
	
	@Autowired
	private ActorService actorService;

	// Test ----------------------------------------------------
	
	/*
	 * Acme Rendezvous:
	 * An actor who is authenticated as a user must be able to:
	 * Create an announcement regarding one of the rendezvouses that he or sheâs creat-ed previously.
	 * REMARK: super authenticated and rendezvous create by he/she previously
	 */
	
	@Test
	public void testCreateAnnouncement(){
		super.authenticate("user2");
		
		Announcement announcement;
		Rendezvous rendezvous;
		Calendar c;
		Date moment;
		
		c = Calendar.getInstance();
		c.set(2018, 02, 15, 20, 00);
		moment = c.getTime();
		
		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		announcement = this.announcementService.create();
		
		announcement.setTitle("announcement");
		announcement.setDescription("description");
		announcement.setMoment(moment);
		announcement.setRendezvous(rendezvous);
		this.announcementService.save(announcement);
		
		super.unauthenticate();	
		
		
	}
	
	/*
	 * Acme Rendezvous:
	 * An actor who is authenticated as a user must be able to:
	 * Create an announcement regarding one of the rendezvouses that he or sheâs creat-ed previously.
	 * REMARK: super authenticated and rendezvous not create by he/she previously
	 */
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAnnouncementRendezvousNotCreatePreviously(){
		super.authenticate("user1");
		
		Announcement announcement;
		Rendezvous rendezvous;
		Calendar c;
		Date moment;
		
		c = Calendar.getInstance();
		c.set(2018, 02, 15, 20, 00);
		moment = c.getTime();
		
		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		announcement = this.announcementService.create();
		
		announcement.setTitle("announcement");
		announcement.setDescription("description");
		announcement.setMoment(moment);
		announcement.setRendezvous(rendezvous);
		this.announcementService.save(announcement);
		
		super.unauthenticate();	
		
		
	}
	
	/*
	 * Acme Rendezvous:
	 * An actor who is authenticated as a user must be able to:
	 * Create an announcement regarding one of the rendezvouses that he or sheâs creat-ed previously.
	 * REMARK: super unauthenticated 
	 */
	
	@Test(expected = IllegalArgumentException.class)
	public void testUnauthenticatedCreateAnnouncement(){
		
		Announcement announcement;
		Rendezvous rendezvous;
		Calendar c;
		Date moment;
		
		c = Calendar.getInstance();
		c.set(2018, 02, 15, 20, 00);
		moment = c.getTime();
		
		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		announcement = this.announcementService.create();
		
		announcement.setTitle("announcement");
		announcement.setDescription("description");
		announcement.setMoment(moment);
		announcement.setRendezvous(rendezvous);
		this.announcementService.save(announcement);
		
		super.unauthenticate();	
		
		
	}
	
	/**
	 * Acme Rendezvous:
	 * An actor who is authenticated as an administrator must be able to:
	 * Remove an announcement that he or she thinks is inappropriate
	 */
	
	@Test
	public void testDeleteAnnouncement(){
		super.authenticate("admin");
		
		Announcement announcement;
		
		announcement = this.announcementService.findOne(super.getEntityId("announcement1"));
		this.announcementService.removeByAdmin(announcement);
		this.announcementService.flush();
		
		super.unauthenticate();
	}
	
	/**
	 * Acme Rendezvous:
	 * An actor who is authenticated as a user must be able to:
	 * Display a stream of announcements that have been posted to the rendezvouses that he or she’s RSVPd. 
	 * The announcements must be listed chronologically in descending order.
	 */
	
	@Test
	public void testStreamAnnouncements(){
		super.authenticate("user1");
		
		Collection<Announcement> announcements;
		Rendezvous rendezvous;
		User user;
		
		user = (User) this.actorService.findByPrincipal();
		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		
		Assert.isTrue(rendezvous.getAttendants().contains(user));
		
		announcements = rendezvous.getAnnouncements();
		
		
		super.unauthenticate();
	}
	
	/**
	 * Acme Rendezvous:
	 * An actor who is authenticated as a user must be able to:
	 * Display a stream of announcements that have been posted to the rendezvouses that he or she’s RSVPd. 
	 * The announcements must be listed chronologically in descending order.
	 * REMARK: rendezvous not rsvp by user
	 */
	
	@Test(expected = IllegalArgumentException.class)
	public void testStreamAnnouncementsRendezvousNotRSVP(){
		super.authenticate("user2");
		
		Collection<Announcement> announcements;
		Rendezvous rendezvous;
		User user;
		
		user = (User) this.actorService.findByPrincipal();
		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		
		Assert.isTrue(rendezvous.getAttendants().contains(user));
		
		announcements = rendezvous.getAnnouncements();
		
		
		super.unauthenticate();
	}
	
	/**
	 * Acme Rendezvous:
	 * An actor who is authenticated as a user must be able to:
	 * Display a stream of announcements that have been posted to the rendezvouses that he or she’s RSVPd. 
	 * The announcements must be listed chronologically in descending order.
	 * REMARK: unauthenticated user
	 */
	
	@Test(expected = IllegalArgumentException.class)
	public void testUnauthenticatedStreamAnnouncements(){
		
		Collection<Announcement> announcements;
		Rendezvous rendezvous;
		User user;
		
		user = (User) this.actorService.findByPrincipal();
		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		
		Assert.isTrue(rendezvous.getAttendants().contains(user));
		
		announcements = rendezvous.getAnnouncements();
		
		
		super.unauthenticate();
	}
	
	
	
	

}
