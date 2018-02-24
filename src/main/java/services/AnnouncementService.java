
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AnnouncementRepository;
import domain.Announcement;
import domain.RSVP;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class AnnouncementService {

	// Managed repository
	// --------------------------------------------------------------------
	@Autowired
	private AnnouncementRepository	announcementRepository;

	@Autowired
	private RendezvousService		rendezvousService;


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

		currentMoment = new Date();
		Assert.isTrue(announcement.getMoment().after(currentMoment));

		result = this.announcementRepository.save(announcement);

		return result;
	}

	public void delete(final Announcement announcement) {
		Assert.isTrue(announcement.getId() != 0);
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.findRendezvousByAnnouncement(announcement.getId());
		this.rendezvousService.removeAnnouncement(rendezvous, announcement);

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

	public Collection<Announcement> getAnnouncementsByUserRsvps(final User user) {
		Collection<RSVP> rsvps;
		Collection<Announcement> announcements;
		Collection<Announcement> res;
		Rendezvous rendezvous;

		rsvps = user.getReserves();
		announcements = Collections.emptyList();
		res = Collections.emptyList();

		for (final RSVP r : rsvps) {
			rendezvous = r.getRendezvous();
			res = rendezvous.getAnnouncements();
			if (res.isEmpty())
				break;
			else
				for (final Announcement a : res)
					announcements.add(a);

		}
		return announcements;

	}

}
