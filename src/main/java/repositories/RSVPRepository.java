
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.RSVP;

@Repository
public interface RSVPRepository extends JpaRepository<RSVP, Integer> {

	@Query("select rsvp from Rendezvous r join r.reserves rsvp where r.id=?1 order by rsvp.id asc")
	Collection<RSVP> findOrderedRSVPByRendezvousId(int rendezvousId);
	
	@Query("select r from RSVP r where r.user.id=?1 and r.rendezvous.id=?2")
    RSVP findRSVPByUserAndRendezvous(int userId,int rendezvousId);
}
