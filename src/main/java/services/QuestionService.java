
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.QuestionRepository;
import domain.Question;

@Service
@Transactional
public class QuestionService {

	// Managed repository --------------------------------------------------------------------
	@Autowired
	private QuestionRepository	questionRepository;


	// Supporting services ------------------------------------------------------------------

	// Constructors ---------------------------------------------------------
	public QuestionService() {
		super();
	}

	// CRUD methods ---------------------------------------------------------

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
