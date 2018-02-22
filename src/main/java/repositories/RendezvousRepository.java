package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rendezvous;

@Repository
public interface RendezvousRepository extends JpaRepository<Rendezvous,Integer> {
	
	/** Level C **/
	/*
	@Query("select avg(u.createdRendezvouses.size), sqrt(sum(u.createdRendezvouses.size*u.createdRendezvouses.size)/count(u.createdRendezvouses.size)-avg(u.createdRendezvouses.size)*avg(u.createdRendezvouses.size)) from User u")
	Double[] avgSqrtRendezvousesPerUser();
	
	@Query("select (sum(case when u.createdRendezvouses is not empty then 1.0 else 0.0 end)/sum(case when u.createdRendezvouses is empty then 1.0 else 0.0 end)) from User u")
	Double ratioOfUsersWithRendezvousVsUsersWithoutRendezvous();
	
	@Query("select avg(u.createdRendezvouses.size), sqrt(sum(u.createdRendezvouses.size*u.createdRendezvouses.size)/count(u.createdRendezvouses.size)-avg(u.createdRendezvouses.size)*avg(u.createdRendezvouses.size)) from Rendezvous r")
	Double[] avgSqrtUsersPerRendezvous();
	
	@Query("select avg(r from Rendezvous r) join r.reserves re where re.user=?1")
	Double[] avgSqrtRendezvousesRSVPdPerUser(int userId);
	 
	@Query("select r from Rendezvous r order by r.attendants.size desc")
	Collection<Rendezvous> top10RendezvousesRSVPd();
	
	
	@Query("select r from Rendezvous r join r.comments c where c.id=?1 and c member of r.comments")
	Rendezvous findRendezvousFromAComment(int commentId);
	
	@Query("select r from Rendezvous r  join r.reserves re where re.user = ?1")
	Collection<Rendezvous> findRendezvousesRSVPByUserId(int userId);
	*/
	/** Level B	**/
	// The rendezvouses that are linked to a number of rendezvouses that is greater than the average plus 10%. 
	@Query("select r1 from Rendezvous r1 where r1.similarOnes.size>(select avg(r2.similarOnes.size)*1.1 from Rendezvous r2)")
	Collection<Rendezvous> rendezvousesLinkedPlus10();
	
	@Query("select r from Rendezvous r where r.finalMode=true")
	Collection<Rendezvous> findAllAvailable();

}
