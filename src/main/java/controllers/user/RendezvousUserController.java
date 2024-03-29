
package controllers.user;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.RendezvousService;
import services.RequestService;
import services.UserService;
import controllers.AbstractController;
import domain.Announcement;
import domain.Rendezvous;
import domain.Request;
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
	private RequestService		requestService;
	
	// Constructors -----------------------------------------------------
	public RendezvousUserController() {
		super();
	}

	// CRUD methods ----------------------------------------------------

	@RequestMapping(value = "/createdRendezvouses", method = RequestMethod.GET)
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

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int rendezvousId) {
		ModelAndView result;
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.findOne(rendezvousId);

		result = this.createEditModelAndView(rendezvous);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Rendezvous rendezvous, BindingResult binding) {
		ModelAndView result;
		Date currentDate;
		
		currentDate = new Date(System.currentTimeMillis()-1);
		
		if (binding.hasErrors())
			result = this.createEditModelAndView(rendezvous);
		else
			try {
				this.rendezvousService.save(rendezvous);
				result = new ModelAndView("redirect:createdRendezvouses.do");
			} catch (final Throwable oops) {
				if(rendezvous.getMoment().before(currentDate)){
					result = this.createEditModelAndView(rendezvous,"rendezvous.date.error");
				} else {
				result = this.createEditModelAndView(rendezvous, "rendezvous.commit.error");
				}
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Rendezvous rendezvous, BindingResult binding) {
		ModelAndView result;

		try {
			this.rendezvousService.delete(rendezvous);
			result = new ModelAndView("redirect:createdRendezvouses.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(rendezvous,
					"rendezvous.commit.error");
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
		Collection<Request> requests;
		boolean isReserved, isCreator;

		try {
			rendezvous = this.rendezvousService.findOne(rendezvousId);
			user = (User) this.actorService.findByPrincipal();

			similarOnes = rendezvous.getSimilarOnes();
			announcements = rendezvous.getAnnouncements();
			requests = this.requestService.findRequestByRendezvous(rendezvous);
			reservedRendezvouses = this.rendezvousService.findRendezvousesRSVPByUserId(user.getId());

			isReserved = reservedRendezvouses.contains(rendezvous);
			isCreator = rendezvous.getCreator().equals(user);

			result = new ModelAndView("rendezvous/display");
			result.addObject("rendezvous", rendezvous);
			result.addObject("similarOnes", similarOnes);
			result.addObject("announcements", announcements);
			result.addObject("isReserved", isReserved);
			result.addObject("isCreator", isCreator);
			result.addObject("requests",requests);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------
	protected ModelAndView createEditModelAndView(final Rendezvous rendezvous) {
		ModelAndView result;

		result = this.createEditModelAndView(rendezvous, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Rendezvous rendezvous, final String messageCode) {
		ModelAndView result;
		Collection<Rendezvous> similarOnes;
		
		similarOnes = this.rendezvousService.findAll();
		similarOnes.remove(rendezvous);
		
		result = new ModelAndView("rendezvous/edit");
		result.addObject("rendezvous", rendezvous);
		result.addObject("similarRendezvouses", similarOnes);
		result.addObject("message", messageCode);

		return result;
	}

}
