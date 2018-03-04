
package controllers.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.RSVPService;
import services.RendezvousService;
import controllers.AbstractController;
import controllers.RendezvousController;
import domain.Answer;
import domain.RSVP;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("/rsvp/user")
public class RSVPUserController extends AbstractController {

	// Services --------------------------------------------------------

	@Autowired
	private RSVPService				rsvpService;

	@Autowired
	private RendezvousService		rendezvousService;

	@Autowired
	private ActorService			actorService;

	// Controllers ------------------------------------------------------

	@Autowired
	private RendezvousController	rendezvousController;


	// Constructors -----------------------------------------------------

	public RSVPUserController() {
		super();
	}

	// CRUD methods ----------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int rendezvousId) {
		ModelAndView result;
		RSVP rsvp;
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.findOne(rendezvousId);

		if (rendezvous.getQuestions().size() > 0)
			result = new ModelAndView("redirect:/question/user/questionnaire.do?rendezvousId=" + rendezvousId);
		else {
			rsvp = this.rsvpService.create(rendezvous);
			try {
				result = this.save(rsvp);
			} catch (final Throwable oops) {
				result = this.rendezvousController.display(rendezvousId);
				result.addObject("message", "rendezvous.commit.error");
			}
		}

		return result;
	}

	public ModelAndView save(final RSVP rsvp) {
		ModelAndView result;

		this.rsvpService.save(rsvp);
		result = new ModelAndView("redirect:/rendezvous/user/list.do");

		return result;
	}

	@RequestMapping(value = "/cancelRSVP", method = RequestMethod.POST, params = "cancelRSVP")
	public ModelAndView cancelRSVP(@Valid final RSVP rsvp, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(rsvp);
		else
			try {
				this.rsvpService.cancel(rsvp);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(rsvp, "rendezvous.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------

	protected ModelAndView createEditModelAndView(final RSVP rsvp) {
		ModelAndView result;

		result = this.createEditModelAndView(rsvp, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final RSVP rsvp, final String messageCode) {
		ModelAndView result;
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.findRendezvousByRSVPId(rsvp.getId());

		result = new ModelAndView("rendezvous/user/display.do?rendezvousId=" + rendezvous.getId());
		result.addObject("rsvp", rsvp);
		result.addObject("message", messageCode);

		return result;
	}

	public ModelAndView answers(final List<Answer> answers, final int rendezvousId) {
		ModelAndView result;
		Rendezvous rendezvous;
		RSVP rsvp;
		User user;

		user = (User) this.actorService.findByPrincipal();
		rendezvous = this.rendezvousService.findOne(rendezvousId);

		rsvp = this.rsvpService.create(rendezvous);
		rsvp.setAnswers(answers);
		rsvp.setUser(user);

		try {
			this.rsvpService.save(rsvp);
			result = new ModelAndView("redirect:/rendezvous/display.do?rendezvousId=" + rendezvousId);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(rsvp, "rendezvous.commit,error");
		}

		return result;

	}

}
