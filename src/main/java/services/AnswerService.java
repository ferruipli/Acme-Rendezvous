
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AnswerRepository;
import domain.Answer;
import domain.Question;
import domain.Rendezvous;
import domain.User;
import forms.QuestionnaireForm;

@Service
@Transactional
public class AnswerService {

	// Managed repository ---------------------------------------------------

	@Autowired
	private AnswerRepository	answerRepository;

	// Supporting services --------------------------------------------------

	@Autowired
	private QuestionService		questionService;

	@Autowired
	private Validator			validator;

	@Autowired
	private UserService			userService;

	@Autowired
	private ActorService		actorService;


	// Constructors ---------------------------------------------------------

	public AnswerService() {
		super();
	}

	// CRUD methods ---------------------------------------------------------

	public Answer create(final int questionId) {
		Answer result;
		Question question;

		question = this.questionService.findOne(questionId);

		result = new Answer();
		result.setQuestion(question);

		return result;
	}

	public Answer save(final Answer answer) {
		Assert.notNull(answer);
		Assert.isTrue(answer.getQuestion().getRendezvous().getFinalMode());
		Assert.isTrue(!answer.getQuestion().getRendezvous().getIsFlagged());

		Answer result;

		result = this.answerRepository.save(answer);

		return result;
	}

	public List<Answer> save(final List<Answer> answers) {
		Assert.notNull(answers);
		List<Answer> result;

		result = this.answerRepository.save(answers);

		return result;
	}

	public void delete(final Answer answer) {
		Assert.notNull(answer);
		Assert.isTrue(answer.getId() != 0);

		this.answerRepository.delete(answer);
	}

	// Other business methods -----------------------------------------------

	/** Crea el listado de Answer a partir de las respuestas guardadas en QuestionnaireForm. Además guarda en la base de datos todas las Answer. **/
	public List<Answer> reconstruct(final QuestionnaireForm questionnaireForm, final BindingResult binding) {
		List<Answer> result;
		List<Question> questions;
		Answer answer;
		int questionId, numberOfQuestions, i;

		this.validateQuestionnaire(questionnaireForm, binding);

		result = new ArrayList<>();
		questions = questionnaireForm.getQuestions();
		numberOfQuestions = questions.size();

		for (i = 0; i < numberOfQuestions; i++) {
			questionId = questions.get(i).getId();
			answer = this.create(questionId);
			answer.setText(questionnaireForm.getAnswers().get(i));
			result.add(answer);

			this.validator.validate(answer, binding);
		}

		return result;
	}

	private void validateQuestionnaire(final QuestionnaireForm questionnaireForm, final BindingResult binding) {
		Rendezvous rendezvous;
		User principal;
		Collection<Question> questionnaireQuestions, rendezvousQuestions;

		rendezvous = questionnaireForm.getRendezvous();
		principal = this.userService.findByPrincipal();
		questionnaireQuestions = questionnaireForm.getQuestions();
		rendezvousQuestions = rendezvous.getQuestions();

		if (!rendezvous.getFinalMode() || rendezvous.getIsFlagged() || rendezvous.getMoment().before(LocalDate.now().toDate()) || rendezvous.getAdultOnly() && this.actorService.getEdad(principal) < 18)
			binding.rejectValue(null, "", "Invalid rendezvous");

		if (questionnaireQuestions.size() != rendezvousQuestions.size() || !questionnaireQuestions.containsAll(rendezvousQuestions))
			binding.rejectValue(null, "", "Questionnaire questions does not match with rendezvous questions");
	}

	public List<Answer> findOrderedAnswersByRendezvousId(final int rendezvousId) {
		List<Answer> result;

		result = new ArrayList<>(this.answerRepository.findOrderedAnswersByRendezvousId(rendezvousId));
		Assert.notNull(result);

		return result;
	}
	
	public void flush() {
		this.answerRepository.flush();
	}

}
