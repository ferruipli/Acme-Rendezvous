
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AnnouncementRepository;
import domain.Announcement;
import domain.Rendezvous;

@Service
@Transactional
public class AnnouncementService {

	// Managed repository
	// --------------------------------------------------------------------
	@Autowired
	private AnnouncementRepository	announcementRepository;

	@Autowired
	private RendezvousService		rendezvousService;

	@Autowired
	private ActorService			actorService;


	// Supporting services
	// ------------------------------------------------------------------

	// Constructors ---------------------------------------------------------
	public AnnouncementService() {
		super();
	}

	// CRUD methods ---------------------------------------------------------
	public Announcement create() {
		Announcement result;
		Date moment;

		result = new Announcement();
		moment = new Date(System.currentTimeMillis() - 1);
		result.setMoment(moment);

		return result;
	}

	public Announcement save(final Announcement announcement) {
		assert announcement != null;

		Announcement result;
		Date currentMoment;
		Rendezvous rendezvous;

		currentMoment = new Date();
		Assert.isTrue(announcement.getMoment().before(currentMoment));

		result = this.announcementRepository.save(announcement);
		rendezvous = result.getRendezvous();

		this.rendezvousService.addAnnouncement(rendezvous, result);

		Assert.isTrue(this.actorService.findByPrincipal() == rendezvous.getCreator());

		return result;
	}

	public void delete(final Announcement announcement) {
		Assert.isTrue(announcement.getId() != 0);
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.findRendezvousByAnnouncement(announcement.getId());
		this.rendezvousService.removeAnnouncement(rendezvous, announcement);

		//Assert.isTrue(this.actorService.findByPrincipal() == rendezvous.getCreator());

		this.announcementRepository.delete(announcement);
	}

	public Announcement findOne(final int announcementId) {
		Assert.isTrue(announcementId != 0);

		Announcement result;

		result = this.announcementRepository.findOne(announcementId);

		Assert.notNull(result);

		return result;
	}

	// Other business methods
	// ------------------------------------------------------------

	public void removeByAdmin(final Announcement announcement) {
		Assert.notNull(announcement);
		Assert.isTrue(announcement.getId() != 0);

		this.announcementRepository.delete(announcement);
	}

	public Double[] avgSqrtAnnouncementsPerRendezvous() {
		Double[] result;

		result = this.announcementRepository.avgSqrtAnnouncementsPerRendezvous();

		return result;
	}

	public Collection<Rendezvous> rendezvousesWhoseMoreThat75Announcements() {
		Collection<Rendezvous> result;

		result = this.announcementRepository.rendezvousesWhoseMoreThat75Announcements();

		return result;
	}

	public Collection<Announcement> findAnnouncementByRSVPUser(final int userId) {
		Collection<Announcement> result;

		result = this.announcementRepository.findAnnouncementByRSVPUser(userId);

		return result;
	}
	
	public void flush() {
		this.announcementRepository.flush();
	}

}
