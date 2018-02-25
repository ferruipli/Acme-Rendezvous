
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

	@Query("select a from Rendezvous rende join rende.reserves r join r.answers a where rende.id=?1 order by r.id asc, a.question.id asc")
	Collection<Answer> findOrderedAnswersByRendezvousId(int rendezvousId);

}
