
package services;

import java.util.Collection;
import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.QuestionRepository;
import domain.Answer;
import domain.Question;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class QuestionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private QuestionRepository	questionRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private UserService			userService;

	@Autowired
	private AnswerService		answerService;


	// Constructors -----------------------------------------------------------

	public QuestionService() {
		super();
	}

	// CRUD methods -----------------------------------------------------------

	public Question create(final int rendezvousId) {
		Question result;
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.findOne(rendezvousId);

		result = new Question();
		result.setAnswers(Collections.<Answer> emptySet());
		result.setRendezvous(rendezvous);

		return result;
	}

	public Question findOne(final int questionId) {
		Question result;

		result = this.questionRepository.findOne(questionId);
		Assert.notNull(result);

		return result;
	}

	public void save(final Question question) {
		Rendezvous rendezvous;
		Question saved;
		User user;

		user = this.userService.findByPrincipal();
		rendezvous = question.getRendezvous();

		Assert.isTrue(!rendezvous.getFinalMode());
		Assert.isTrue(!rendezvous.getIsFlagged());
		Assert.isTrue(rendezvous.getCreator().equals(user));

		saved = this.questionRepository.save(question);

		if (question.getId() == 0)
			this.rendezvousService.addQuestion(rendezvous, saved);
	}

	public void delete(final Question question) {
		Assert.notNull(question);
		Assert.isTrue(question.getId() != 0);

		Collection<Answer> answers;

		answers = question.getAnswers();

		// Deleting all answers involves with this question
		if (answers != null && !answers.isEmpty())
			for (final Answer a : answers)
				this.answerService.delete(a);

		this.questionRepository.delete(question);
	}

	public void deleteEditableQuestion(final Question question) {
		Assert.notNull(question);
		Assert.isTrue(question.getId() != 0);

		Rendezvous rendezvous;
		User user;

		user = this.userService.findByPrincipal();
		rendezvous = question.getRendezvous();

		Assert.isTrue(!rendezvous.getFinalMode());
		Assert.isTrue(!rendezvous.getIsFlagged());
		Assert.isTrue(rendezvous.getCreator().equals(user));

		this.rendezvousService.removeQuestion(rendezvous, question);
		this.questionRepository.delete(question);
	}

	// Other business methods -------------------------------------------------

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

}
