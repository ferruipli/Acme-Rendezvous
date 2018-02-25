
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnswerService;
import services.QuestionService;
import services.RSVPService;
import services.RendezvousService;
import services.UserService;
import domain.Answer;
import domain.Question;
import domain.RSVP;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("/question")
public class QuestionAnswerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private UserService			userService;

	@Autowired
	private RSVPService			rsvpService;

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private QuestionService		questionService;

	@Autowired
	private AnswerService		answerService;


	// Constructors -----------------------------------------------------------

	public QuestionAnswerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int rendezvousId) {
		ModelAndView result;
		RSVP aux;
		Collection<User> users;
		List<RSVP> rsvps;
		List<Question> questions;
		List<Answer> answers;
		Rendezvous rendezvous;
		boolean editable;
		User principal;
		Integer numberOfQuestions;

		numberOfQuestions = null;
		questions = new ArrayList<>();
		rendezvous = this.rendezvousService.findOne(rendezvousId);
		rsvps = this.rsvpService.findOrderedRSVPByRendezvousId(rendezvousId);
		answers = this.answerService.findOrderedAnswersByRendezvousId(rendezvousId);
		users = rendezvous.getAttendants();

		try {
			principal = this.userService.findByPrincipal();
		} catch (final IllegalArgumentException oops) {
			principal = null;
		}
		editable = !rendezvous.getFinalMode() && rendezvous.getCreator().equals(principal);

		if (rsvps.size() != 0) {
			aux = rsvps.get(0);
			questions = this.questionService.findOrderedQuestionsByRSVPId(aux.getId());
			numberOfQuestions = questions.size();
		}

		result = new ModelAndView("questionAnswer/list");
		result.addObject("requestURI", "question/list.do");
		result.addObject("users", users);
		result.addObject("rsvps", rsvps);
		result.addObject("questions", questions);
		result.addObject("answers", answers);
		result.addObject("editable", editable);
		result.addObject("numberOfQuestions", numberOfQuestions);

		return result;
	}
}
