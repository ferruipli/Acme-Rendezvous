package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Announcement;
import domain.Rendezvous;

public interface AnnouncementRepository extends
		JpaRepository<Announcement, Integer> {

	/** Level B **/
	@Query("")
	Double[] avgSqrtAnnouncementsPerRendezvous();
	
	// The rendezvouses that whose number of announcements is above 75% the average number of announcements per rendezvous. 
	@Query("")
	Collection<Rendezvous> rendezvousesWhoseMoreThat75Announcements();
}
