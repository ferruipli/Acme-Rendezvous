
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rendezvous;

@Repository
public interface RendezvousRepository extends JpaRepository<Rendezvous, Integer> {

	@Query("select avg(u.createdRendezvouses.size), sqrt(sum(u.createdRendezvouses.size*u.createdRendezvouses.size)/count(u.createdRendezvouses.size)-avg(u.createdRendezvouses.size)*avg(u.createdRendezvouses.size)) from User u")
	Double[] avgSqrtRendezvousesPerUser();

	@Query("select (sum(case when u.createdRendezvouses is not empty then 1.0 else 0.0 end)/sum(case when u.createdRendezvouses is empty then 1.0 else 0.0 end)) from User u")
	Double ratioOfUsersWithRendezvousVsUsersWithoutRendezvous();

	@Query("select avg(r.attendants.size), sqrt(sum(r.attendants.size*r.attendants.size)/count(r.attendants.size)-avg(r.attendants.size)*avg(r.attendants.size)) from Rendezvous r")
	Double[] avgSqrtUsersPerRendezvous();

	@Query("select avg(u.reserves.size), sqrt(sum(u.reserves.size*u.reserves.size)/count(u.reserves.size)-avg(u.reserves.size)*avg(u.reserves.size)) from User u")
	Double[] avgSqrtRendezvousesRSVPdPerUser();

	@Query("select r from Rendezvous r order by r.attendants.size desc")
	Page<Rendezvous> top10RendezvousesRSVPd(Pageable page);

	@Query("select r from Rendezvous r join r.comments c where c.id=?1 and c member of r.comments")
	Rendezvous findRendezvousFromAComment(int commentId);

	@Query("select r from Rendezvous r  join r.reserves re where re.user = ?1")
	Collection<Rendezvous> findRendezvousesRSVPByUserId(int userId);

	@Query("select r1 from Rendezvous r1 where r1.similarOnes.size>(select avg(r2.similarOnes.size)*1.1 from Rendezvous r2)")
	Collection<Rendezvous> rendezvousesLinkedPlus10();

	@Query("select r from Rendezvous r where r.finalMode=true")
	Collection<Rendezvous> findAllAvailable();

	@Query("select r from Rendezvous r join r.announcements a where a.id=? and a member of r.announcements")
	Rendezvous findRendezvousByAnnouncement(int announcementId);

}
