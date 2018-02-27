
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnswerService;
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
	private AnswerService		answerService;


	// Constructors -----------------------------------------------------------

	public QuestionAnswerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int rendezvousId) {
		ModelAndView result;
		Collection<User> users;
		List<RSVP> rsvps;
		List<Question> questions;
		List<Answer> answers;
		Rendezvous rendezvous;
		boolean editable;
		User principal;
		Integer numberOfQuestions;

		rendezvous = this.rendezvousService.findOne(rendezvousId);
		questions = new ArrayList<>(this.rendezvousService.findOrderedQuestionsByRendezvousId(rendezvousId));
		numberOfQuestions = questions.size();
		rsvps = this.rsvpService.findOrderedRSVPByRendezvousId(rendezvousId);
		answers = this.answerService.findOrderedAnswersByRendezvousId(rendezvousId);
		users = this.rendezvousService.findOne(rendezvousId).getAttendants();

		try {
			principal = this.userService.findByPrincipal();
		} catch (final IllegalArgumentException oops) {
			principal = null;
		}
		editable = !rendezvous.getFinalMode() && rendezvous.getCreator().equals(principal);

		// Solo se puede mostrar el listado de preguntas cuando el principal sea el creador o cuando el principal no sea creador y finalMode=true.
		Assert.isTrue(rendezvous.getFinalMode() || rendezvous.getCreator().equals(principal));

		result = new ModelAndView("questionAnswer/list");
		result.addObject("requestURI", "question/list.do");
		result.addObject("users", users);
		result.addObject("rsvps", rsvps);
		result.addObject("questions", questions);
		result.addObject("answers", answers);
		result.addObject("editable", editable);
		result.addObject("numberOfQuestions", numberOfQuestions);
		result.addObject("rendezvousId", rendezvousId);

		return result;
	}
}
