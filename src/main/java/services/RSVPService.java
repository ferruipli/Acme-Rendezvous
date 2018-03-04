
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RSVPRepository;
import domain.Answer;
import domain.RSVP;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class RSVPService {

	// Managed repository --------------------------------------------------------------------

	@Autowired
	private RSVPRepository		rsvpRepository;

	// Supporting services ------------------------------------------------------------------

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private ActorService		actorService;
	
	@Autowired
	private UserService			userService;


	// Constructors ---------------------------------------------------------

	public RSVPService() {
		super();
	}

	// CRUD methods ---------------------------------------------------------

	public RSVP create(final Rendezvous rendezvous) {
		RSVP result;
		User user;

		result = new RSVP();
		user = (User) this.actorService.findByPrincipal();

		result.setAnswers(Collections.<Answer> emptySet());
		result.setUser(user);
		result.setRendezvous(rendezvous);

		return result;

	}

	public RSVP findOne(final int rsvpId) {
		Assert.notNull(rsvpId != 0);

		RSVP result;

		result = this.rsvpRepository.findOne(rsvpId);

		return result;
	}

	public Collection<RSVP> findAll() {
		Collection<RSVP> results;

		results = this.rsvpRepository.findAll();

		return results;
	}

	public RSVP save(final RSVP rsvp) {
		Assert.notNull(rsvp);
		RSVP result;
		Date date;
		User user;
		long edad;
		Rendezvous rendezvous;

		user = (User) this.actorService.findByPrincipal();
		edad = this.actorService.getEdad(user);
		rendezvous = this.rendezvousService.findRendezvousByRSVPId(rsvp.getId());
		
		rsvp.setUser(user);
		rsvp.setAnswers(Collections.<Answer> emptySet());
		rsvp.setRendezvous(rendezvous);
		
		if(rsvp.getRendezvous().getAdultOnly()==true){
			Assert.isTrue(edad>=18);
		}
		date = new Date(System.currentTimeMillis());

		Assert.isTrue(rsvp.getRendezvous().getMoment().after(date));

		this.rendezvousService.addAttendant(rsvp.getRendezvous(), user);
		result = this.rsvpRepository.save(rsvp);

		return result;
	}

	// Other business methods ------------------------------------------------------------

	public void cancel(final RSVP rsvp) {
		Assert.isTrue(rsvp.getId() != 0);
		User user;

		user = (User) this.actorService.findByPrincipal();

		this.rendezvousService.removeAttendant(rsvp.getRendezvous(), user);
		this.rsvpRepository.delete(rsvp);

	}

	public void removeByAdmin(RSVP rsvp) {
		Assert.notNull(rsvp);
		Assert.isTrue(rsvp.getId() != 0);
		
		// Update User::reserves
		this.userService.removeRSVP(rsvp.getUser(),rsvp);
			
		this.rsvpRepository.delete(rsvp);
	}
	
	public List<RSVP> findOrderedRSVPByRendezvousId(final int rendezvousId) {
		List<RSVP> result;

		result = new ArrayList<>(this.rsvpRepository.findOrderedRSVPByRendezvousId(rendezvousId));
		Assert.notNull(result);

		return result;
	}

	public RSVP findRSVPByUserAndRendezvous(final int userId, final int rendezvousId){
		RSVP result;
		
		result = this.rsvpRepository.findRSVPByUserAndRendezvous(userId, rendezvousId);
		
		return result;
	}
}
