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
	private AnnouncementRepository announcementRepository;

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

	// Other business methods
	// ------------------------------------------------------------

	public Double[] avgSqrtAnnouncementsPerRendezvous() {
		Double[] result;

		result = this.announcementRepository
				.avgSqrtAnnouncementsPerRendezvous();

		return result;
	}

	public Collection<Rendezvous> rendezvousesWhoseMoreThat75Announcements() {
		Collection<Rendezvous> result;

		result = this.announcementRepository
				.rendezvousesWhoseMoreThat75Announcements();

		return result;
	}

}
