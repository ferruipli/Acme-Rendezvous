
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.RSVP;
import domain.Rendezvous;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RsvpServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private RSVPService			rsvpService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private UserService			userService;


	// Tests ------------------------------------------------------------------
	/**
	 * AcmeRendezvous Req 5.4
	 * RSVP a rendezvous or cancel it. When a user RSVPs a rendezvous, he or she is assumed to attend it.
	 */
	@Test
	public void UserRsvpRendezvousTest() {
		int userId;
		User user;
		Rendezvous rendezvous;
		RSVP rsvp;

		userId = super.getEntityId("user1");

		super.authenticate("user1");

		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous5"));
		rsvp = this.rsvpService.create(rendezvous);
		user = this.userService.findOne(userId);

		rsvp.setUser(user);

		this.rsvpService.save(rsvp);

		super.unauthenticate();
	}

	/**
	 * AcmeRendezvous Req 5.4
	 * RSVP a rendezvous or cancel it. When a user RSVPs a rendezvous, he or she is assumed to attend it.
	 * NOTE: Intenta reservar a una cita ya pasada
	 */
	@Test(expected = IllegalArgumentException.class)
	public void pastRendezvousRsvpRendezvousTest() {
		int userId;
		User user;
		Rendezvous rendezvous;
		RSVP rsvp;

		userId = super.getEntityId("user1");

		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		rsvp = this.rsvpService.create(rendezvous);
		user = this.userService.findOne(userId);

		rsvp.setUser(user);

		this.rsvpService.save(rsvp);

		super.unauthenticate();
	}

	/**
	 * AcmeRendezvous Req 5.4
	 * RSVP a rendezvous or cancel it. When a user RSVPs a rendezvous, he or she is assumed to attend it.
	 * NOTE: Intenta reservar a una cita ya pasada
	 */
	@Test(expected = IllegalArgumentException.class)
	public void nullRsvpRendezvousTest() {
		int userId;
		User user;
		RSVP rsvp;

		userId = super.getEntityId("user1");

		rsvp = this.rsvpService.create(null);
		user = this.userService.findOne(userId);

		rsvp.setUser(user);

		this.rsvpService.save(rsvp);

		super.unauthenticate();
	}

	/**
	 * AcmeRendezvous Req 5.4
	 * RSVP a rendezvous or cancel it. When a user RSVPs a rendezvous, he or she is assumed to attend it.
	 */
	@Test
	public void cancelRsvpRendezvousTest() {
		RSVP rsvp;

		super.authenticate("user1");

		rsvp = this.rsvpService.findOne(1123);

		this.rsvpService.cancel(rsvp);

		super.unauthenticate();

	}

	/**
	 * AcmeRendezvous Req 5.4
	 * RSVP a rendezvous or cancel it. When a user RSVPs a rendezvous, he or she is assumed to attend it.
	 */
	@Test
	public void cancel2RendezvousRsvpRendezvousTest() {
		RSVP rsvp;

		super.authenticate("user1");

		rsvp = this.rsvpService.findOne(1124);

		this.rsvpService.cancel(rsvp);

		super.unauthenticate();

	}

	/**
	 * AcmeRendezvous Req 5.4
	 * RSVP a rendezvous or cancel it. When a user RSVPs a rendezvous, he or she is assumed to attend it.
	 * NOTE: Cancelar un rsvp no guardado
	 */
	@Test(expected = IllegalArgumentException.class)
	public void cancelNotSavedRsvpRendezvousTest() {
		int userId;
		User user;
		RSVP rsvp;

		userId = super.getEntityId("user1");

		rsvp = this.rsvpService.create(null);
		user = this.userService.findOne(userId);

		rsvp.setUser(user);

		this.rsvpService.cancel(rsvp);

		super.unauthenticate();
	}

}
