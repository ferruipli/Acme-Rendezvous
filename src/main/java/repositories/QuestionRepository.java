package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{

	/** Level A **/
	@Query("")
	Double[] avgSqrtQuestionsPerRendezvous();
	
	@Query("")
	Double[] avgSqrtAnswersToQuestionsPerRendezvous(); // ¿Aquí o en answerRepository?
}
