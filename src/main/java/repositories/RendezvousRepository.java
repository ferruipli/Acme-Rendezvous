package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rendezvous;
import domain.User;

@Repository
public interface RendezvousRepository extends JpaRepository<Rendezvous,Integer> {

	/** Level C **/
	@Query("select avg(u.createdRendezvouses.size), sqrt(sum(u.createdRendezvouses.size*u.createdRendezvouses.size)/count(u.createdRendezvouses.size)-avg(u.createdRendezvouses.size)*avg(u.createdRendezvouses.size)) from User u")
	Double[] avgSqrtRendezvousesPerUser();
	
	@Query("select (sum(case when u.createdRendezvouses is not empty then 1.0 else 0.0 end)/sum(case when u.createdRendezvouses is empty then 1.0 else 0.0 end)) from User u")
	Double ratioOfUsersWithRendezvousVsUsersWithoutRendezvous();
	
	//TODO: @Query("select avg(u.createdRendezvouses.size), sqrt(sum(u.createdRendezvouses.size*u.createdRendezvouses.size)/count(u.createdRendezvouses.size)-avg(u.createdRendezvouses.size)*avg(u.createdRendezvouses.size)) from Rendezvous r")
	Double[] avgSqrtUsersPerRendezvous();
	
	//TODO: @Query("")
	Double[] avgSqrtRendezvousesRSVPdPerUser();
	
	//TODO: The top-10 rendezvouses in terms of users who have RSVPd them. 
	@Query("")
	Collection<Rendezvous> top10RendezvousesRSVPd();
	
	// TODO: Colección de Rendezvous reservadas de un user
	@Query("")
	Collection<Rendezvous> findRendezvousReservedByUser(User user);
	
	/** Level B	**/
	// TODO: The rendezvouses that are linked to a number of rendezvouses that is greater than the average plus 10%. 
	Collection<Rendezvous> rendezvousesLinkedPlus10();
	
	@Query("select r from Rendezvous r where r.finalModel=true")
	Collection<Rendezvous> findAllAvailable();

}
