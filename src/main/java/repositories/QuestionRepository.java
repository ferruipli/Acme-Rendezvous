
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	/** Level A **/
	@Query("select avg(r.questions.size), sqrt(sum(r.questions.size*r.questions.size)/count(r.questions.size)-avg(r.questions.size)*avg(r.questions.size)) from Rendezvous r")
	Double[] avgSqrtQuestionsPerRendezvous();

	@Query("select avg(q.answers.size), sqrt(sum(q.answers.size*q.answers.size)/count(q.answers.size)-avg(q.answers.size)*avg(q.answers.size)) from Rendezvous r join r.questions q")
	Double[] avgSqrtAnswersToQuestionsPerRendezvous(); // ¿Aquí o en answerRepository?

	@Query("select q from RSVP r join r.rendezvous.questions q where r.id=?1 order by q.id asc")
	Collection<Question> findOrderedQuestionsByRSVPId(int rsvpId);
		
}
