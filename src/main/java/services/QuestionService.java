
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.QuestionRepository;
import domain.Answer;
import domain.Question;

@Service
@Transactional
public class QuestionService {

	// Managed repository --------------------------------------------------------------------
	@Autowired
	private QuestionRepository	questionRepository;


	// Supporting services ------------------------------------------------------------------
	@Autowired
	private AnswerService answerService;
	
	// Constructors ---------------------------------------------------------
	public QuestionService() {
		super();
	}

	// CRUD methods ---------------------------------------------------------
	public void delete(Question question) {
		Assert.notNull(question);
		Assert.isTrue(question.getId() != 0);
		
		Collection<Answer> answers;
		
		answers = question.getAnswers();
		
		// Deleting all answers involves with this question
		if (answers != null && !answers.isEmpty()) {
			for (Answer a: answers) {
				this.answerService.delete(a);
			}
		}
		
		this.questionRepository.delete(question);
	}
	
	
	// Other business methods ------------------------------------------------------------

	public Double[] avgSqrtQuestionsPerRendezvous() {
		Double[] result;

		result = this.questionRepository.avgSqrtQuestionsPerRendezvous();

		return result;
	}

	public Double[] avgSqrtAnswersToQuestionsPerRendezvous() {
		Double[] result;

		result = this.questionRepository.avgSqrtAnswersToQuestionsPerRendezvous();

		return result;
	}

	public List<Question> findOrderedQuestionsByRSVPId(final int rsvpId) {
		List<Question> result;

		result = new ArrayList<>(this.questionRepository.findOrderedQuestionsByRSVPId(rsvpId));
		Assert.notNull(result);

		return result;
	}
	
}
