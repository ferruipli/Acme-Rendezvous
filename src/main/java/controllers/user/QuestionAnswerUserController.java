
package controllers.user;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnswerService;
import services.QuestionService;
import services.RendezvousService;
import controllers.AbstractController;
import domain.Answer;
import domain.Question;
import domain.Rendezvous;
import forms.QuestionnaireForm;

@Controller
@RequestMapping("/question/user")
public class QuestionAnswerUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private QuestionService		questionService;

	@Autowired
	private AnswerService		answerService;

	@Autowired
	private RendezvousService	rendezvousService;

	// Controllers ------------------------------------------------------------

	@Autowired
	private RSVPUserController	rsvpUserController;


	// Constructors -----------------------------------------------------------

	public QuestionAnswerUserController() {
		super();
	}

	// Create question ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createQuestion(@RequestParam final int rendezvousId) {
		ModelAndView result;
		Question question;

		question = this.questionService.create(rendezvousId);

		result = this.createEditQuestionModelAndView(question);

		return result;
	}

	// Edit question ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editQuestion(@RequestParam final int questionId) {
		ModelAndView result;
		Question question;

		question = this.questionService.findOne(questionId);

		result = this.createEditQuestionModelAndView(question);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveQuestion(@Valid final Question question, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditQuestionModelAndView(question);
		else
			try {
				this.questionService.save(question);
				result = new ModelAndView("redirect:/question/list.do?rendezvousId=" + question.getRendezvous().getId());
			} catch (final Throwable oops) {
				result = this.createEditQuestionModelAndView(question, "question.error.commit");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteQuestion(final Question question, final BindingResult binding) {
		ModelAndView result;

		try {
			this.questionService.deleteEditableQuestion(question);
			result = new ModelAndView("redirect:/question/list.do?rendezvousId=" + question.getRendezvous().getId());
		} catch (final Throwable oops) {
			result = this.createEditQuestionModelAndView(question, "question.error.commit");
		}

		return result;
	}

	// Create questionnaire ---------------------------------------------------

	@RequestMapping(value = "/questionnaire", method = RequestMethod.GET)
	public ModelAndView createQuestionnaire(@RequestParam final int rendezvousId) {
		ModelAndView result;
		Rendezvous rendezvous;
		List<Question> questions;
		List<String> answers;
		QuestionnaireForm questionnaireForm;

		rendezvous = this.rendezvousService.findOne(rendezvousId);
		questions = new ArrayList<>(rendezvous.getQuestions());
		answers = new ArrayList<>();

		Assert.isTrue(questions.size() > 0);

		questionnaireForm = new QuestionnaireForm();
		questionnaireForm.setAnswers(answers);
		questionnaireForm.setQuestions(questions);
		questionnaireForm.setCurrentQuestionNumber(0);

		result = this.answerModelAndView(questionnaireForm);

		return result;
	}

	// Next question ----------------------------------------------------------

	@RequestMapping(value = "/questionnaire", method = RequestMethod.POST, params = "next")
	public ModelAndView nextQuestion(final QuestionnaireForm questionnaireForm, final BindingResult binding) {
		ModelAndView result;
		String currentAnswerText;

		currentAnswerText = this.answerService.getValidatedAnswerText(questionnaireForm, binding);
		if (binding.hasErrors())
			result = this.answerModelAndView(questionnaireForm);
		else
			try {
				this.iterateQuestionnaireForm(questionnaireForm, currentAnswerText);
				result = this.answerModelAndView(questionnaireForm);
			} catch (final Throwable oops) {
				result = this.answerModelAndView(questionnaireForm, "question.error.commit");
			}

		return result;
	}

	// Finish questionnaire ---------------------------------------------------

	@RequestMapping(value = "/questionnaire", method = RequestMethod.POST, params = "finish")
	public ModelAndView finishQuestionnaire(final QuestionnaireForm questionnaireForm, final BindingResult binding) {
		ModelAndView result;
		String lastAnswerText;
		List<Answer> answers;
		int rendezvousId;

		lastAnswerText = this.answerService.getValidatedAnswerText(questionnaireForm, binding);
		if (binding.hasErrors())
			result = this.answerModelAndView(questionnaireForm);
		else
			try {
				questionnaireForm.getAnswers().add(lastAnswerText);
				Assert.isTrue(questionnaireForm.getAnswers().size() == questionnaireForm.getQuestions().size());
				answers = this.answerService.reconstructAndSaveAnswers(questionnaireForm, binding);
				rendezvousId = this.rendezvousService.findRendezvousByAnswerId(answers.get(0).getId());
				result = this.rsvpUserController.answers(answers, rendezvousId);
			} catch (final Throwable oops) {
				result = this.answerModelAndView(questionnaireForm, "question.error.commit");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	private void iterateQuestionnaireForm(final QuestionnaireForm questionnaireForm, final String currentAnswerText) {
		int nextQuestionNumber;

		nextQuestionNumber = questionnaireForm.getCurrentQuestionNumber() + 1;
		questionnaireForm.getAnswers().add(currentAnswerText);
		questionnaireForm.setCurrentQuestionNumber(nextQuestionNumber);
		questionnaireForm.setText("");
	}

	protected ModelAndView answerModelAndView(final QuestionnaireForm questionnaireForm) {
		ModelAndView result;

		result = this.answerModelAndView(questionnaireForm, null);

		return result;
	}

	protected ModelAndView answerModelAndView(final QuestionnaireForm questionnaireForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("questionAnswer/answer");
		result.addObject("message", messageCode);
		result.addObject("questionnaireForm", questionnaireForm);
		result.addObject("numberOfQuestions", questionnaireForm.getQuestions().size());

		return result;
	}

	protected ModelAndView createEditQuestionModelAndView(final Question question) {
		ModelAndView result;

		result = this.createEditQuestionModelAndView(question, null);

		return result;
	}

	protected ModelAndView createEditQuestionModelAndView(final Question question, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("questionAnswer/edit");
		result.addObject("message", messageCode);
		result.addObject("question", question);
		result.addObject("rendezvousId", question.getRendezvous().getId());

		return result;
	}
}
