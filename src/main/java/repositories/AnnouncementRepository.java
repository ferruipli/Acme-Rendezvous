package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Announcement;
import domain.Rendezvous;

@Repository
public interface AnnouncementRepository extends
		JpaRepository<Announcement, Integer> {

	/** Level B **/
	@Query("select avg(r.announcements.size), sqrt(sum(r.announcements.size*r.announcements.size)/count(r.announcements.size)-avg(r.announcements.size)*avg(r.announcements.size)) from Rendezvous r")
	Double[] avgSqrtAnnouncementsPerRendezvous();
	
	// TODO: The rendezvouses that whose number of announcements is above 75% the average number of announcements per rendezvous. 
	@Query("")
	Collection<Rendezvous> rendezvousesWhoseMoreThat75Announcements();
}
