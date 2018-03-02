
package controllers.user;

import java.util.Collection;
import java.util.Collections;

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
import services.UserService;
import controllers.AbstractController;
import domain.Announcement;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("/rendezvous/user")
public class RendezvousUserController extends AbstractController {

	// Services --------------------------------------------------------
	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserService			userService;
	
	@Autowired
	private RSVPService			rsvpService;

	// Constructors -----------------------------------------------------
	public RendezvousUserController() {
		super();
	}

	// CRUD methods ----------------------------------------------------

	@RequestMapping(value="/createdRendezvouses", method = RequestMethod.GET)
	public ModelAndView listOfCreatedRendezvous() {
		ModelAndView result;
		Collection<Rendezvous> rendezvouses;
		User user;
		
		user = this.userService.findByPrincipal();
		rendezvouses = user.getCreatedRendezvouses();
		
		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("isReserved", true);
		result.addObject("requestURI", "rendezvous/user/createdRendezvouses");
		
		return result;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Rendezvous> rendezvouses;
		User user;
		boolean isReserved;
		
		isReserved = true;

		user = (User) this.actorService.findByPrincipal();
		rendezvouses = this.rendezvousService.findRendezvousesRSVPByUserId(user.getId());

		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("isReserved", isReserved);
		result.addObject("requestURI", "rendezvous/user/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.create();
		result = this.createEditModelAndView(rendezvous);

		return result;
	}
	/*
	@RequestMapping(value="/display", method = RequestMethod.POST, params = "rsvp")
	public ModelAndView doRSVP(@RequestParam int rendezvousId) {
		ModelAndView result;
		Rendezvous rendezvous;
		String redirect;
		
		rendezvous = this.rendezvousService.findOne(rendezvousId);
		
		if (rendezvous.getQuestions().size()>0) {
			redirect = "redirect:question/user/questionnaire.do?rendezvousId=" + rendezvousId; 
			result = new ModelAndView(redirect);
		} else {
			
		}
		
		try {
			this.rsvpService.save(rsvp);
			result = new ModelAndView("redirect:createdRendezvouses.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(rendezvous,
					"rendezvous.commit.error");
		}

		return result;
	}
	*/
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int rendezvousId) {
		ModelAndView result;
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.findOne(rendezvousId);
		
		result = this.createEditModelAndView(rendezvous);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Rendezvous rendezvous, final BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors())
			result = this.createEditModelAndView(rendezvous);
		else
			try {
				this.rendezvousService.save(rendezvous);
				result = new ModelAndView("redirect:createdRendezvouses.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(rendezvous,
						"rendezvous.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Rendezvous rendezvous, final BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(rendezvous);
		} else {
			try {
				this.rendezvousService.delete(rendezvous);
				result = new ModelAndView("redirect:createdRendezvouses.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(rendezvous,
						"rendezvous.commit.error");
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int rendezvousId) {
		ModelAndView result;
		User user;
		Rendezvous rendezvous;
		Collection<Rendezvous> similarOnes, reservedRendezvouses;
		Collection<Announcement> announcements;
		boolean isReserved, isCreator;

		rendezvous = this.rendezvousService.findOne(rendezvousId);
		user = (User) this.actorService.findByPrincipal();

		similarOnes = rendezvous.getSimilarOnes();
		announcements = rendezvous.getAnnouncements();

		reservedRendezvouses = this.rendezvousService.findRendezvousesRSVPByUserId(user.getId());

		isReserved = reservedRendezvouses.contains(rendezvous);
		isCreator = rendezvous.getCreator().equals(user);

		result = new ModelAndView("rendezvous/display");
		result.addObject("rendezvous", rendezvous);
		result.addObject("similarOnes", similarOnes);
		result.addObject("announcements", announcements);
		result.addObject("isReserved", isReserved);
		result.addObject("isCreator", isCreator);

		return result;
	}

	// Arcillary methods ------------------------------------------------
	protected ModelAndView createEditModelAndView(final Rendezvous rendezvous) {
		ModelAndView result;

		result = this.createEditModelAndView(rendezvous, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Rendezvous rendezvous, final String messageCode) {
		ModelAndView result;
		Collection<Rendezvous> similarOnes;
		User user;
		
		user = this.userService.findByPrincipal();
		similarOnes = user.getCreatedRendezvouses();
		similarOnes.remove(rendezvous);
		
		result = new ModelAndView("rendezvous/edit");
		result.addObject("rendezvous", rendezvous);
		result.addObject("similarRendezvouses", similarOnes);
		result.addObject("message", messageCode);

		return result;
	}

}
