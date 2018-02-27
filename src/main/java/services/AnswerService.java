
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.AnswerRepository;
import domain.Answer;
import domain.Question;
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

	public void save(final Answer answer) {
		Assert.isTrue(answer.getQuestion().getRendezvous().getFinalMode());
		Assert.isTrue(!answer.getQuestion().getRendezvous().getIsFlagged());

		this.answerRepository.save(answer);
	}

	public void delete(final Answer answer) {
		Assert.notNull(answer);
		Assert.isTrue(answer.getId() != 0);

		this.answerRepository.delete(answer);
	}

	// Other business methods -----------------------------------------------

	/** Crea el listado de Answer a partir de las respuestas guardadas en QuestionnaireForm **/
	public List<Answer> reconstructAnswers(final QuestionnaireForm questionnaireForm, final BindingResult binding) {
		List<Answer> result;
		Answer answer;
		int questionId, numberOfQuestions, i;

		result = new ArrayList<>();
		numberOfQuestions = questionnaireForm.getQuestions().size();

		for (i = 0; i < numberOfQuestions; i++) {
			questionId = questionnaireForm.getQuestions().get(i).getId();
			answer = this.create(questionId);
			answer.setText(questionnaireForm.getAnswers().get(i));
			result.add(answer);
		}

		return result;
	}

	/** Valida el texto de la última respuesta registrada **/
	public String getValidatedAnswerText(final QuestionnaireForm questionnaireForm, final BindingResult binding) {
		String result;

		result = questionnaireForm.getText();
		if ("".equals(result))
			binding.rejectValue("text", "question.error.blank", "Must not be blank");

		return result;
	}

	public List<Answer> findOrderedAnswersByRendezvousId(final int rendezvousId) {
		List<Answer> result;

		result = new ArrayList<>(this.answerRepository.findOrderedAnswersByRendezvousId(rendezvousId));
		Assert.notNull(result);

		return result;
	}

}
