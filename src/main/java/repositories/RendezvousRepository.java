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
	@Query("")
	Double[] avgSqrtRendezvousesPerUser();
	
	@Query("")
	Double ratioOfUsersWithRendezvousVsUsersWithoutRendezvous();
	
	@Query("")
	Double[] avgSqrtUsersPerRendezvous();
	
	@Query("")
	Double[] avgSqrtRendezvousesRSVPdPerUser();
	
	//The top-10 rendezvouses in terms of users who have RSVPd them. 
	@Query("")
	Collection<Rendezvous> top10RendezvousesRSVPd();
	
	// Colección de Rendezvous reservadas de un user
	@Query("")
	Collection<Rendezvous> findRendezvousReservedByUser(User user);
	
	/** Level B	**/
	// The rendezvouses that are linked to a number of rendezvouses that is greater than the average plus 10%. 
	Collection<Rendezvous> rendezvousesLinkedPlus10();
	

}
