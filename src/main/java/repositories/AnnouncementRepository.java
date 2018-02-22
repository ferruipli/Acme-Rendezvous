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
	
	@Query("select r from Rendezvous r where r.announcements.size/(select avg(r1.announcements.size) from Rendezvous r1)>7.5")
	Collection<Rendezvous> rendezvousesWhoseMoreThat75Announcements();
}
