
package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.RendezvousService;
import controllers.AbstractController;
import domain.Announcement;
import domain.Rendezvous;
import domain.User;
import forms.GPSForm;
import forms.RendezvousForm;

@Controller
@RequestMapping("/rendezvous/user")
public class RendezvousUserController extends AbstractController {

	// Services --------------------------------------------------------
	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------
	public RendezvousUserController() {
		super();
	}

	// CRUD methods ----------------------------------------------------

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
		result.addObject("requestMapping", "rendezvous/user/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		RendezvousForm rendezvousForm;

		rendezvousForm = new RendezvousForm();

		result = this.createEditModelAndView(rendezvousForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int rendezvousId) {
		ModelAndView result;
		Rendezvous rendezvous;
		RendezvousForm rendezvousForm;
		GPSForm	gpsForm;

		rendezvous = this.rendezvousService.findOne(rendezvousId);
		rendezvousForm = new RendezvousForm();
		gpsForm = new GPSForm();
		
		gpsForm.setLatitude(rendezvous.getGpsCoordinates().getLatitude());
		gpsForm.setLongitude(rendezvous.getGpsCoordinates().getLongitude());
		
		rendezvousForm.setName(rendezvous.getName());
		rendezvousForm.setDescription(rendezvous.getDescription());
		rendezvousForm.setMoment(rendezvous.getMoment());
		rendezvousForm.setFinalMode(rendezvous.getFinalMode());
		rendezvousForm.setAdultOnly(rendezvous.getAdultOnly());
		rendezvousForm.setGpsCoordinates(gpsForm);
		rendezvousForm.setSimilarOnes(rendezvous.getSimilarOnes());
		
		result = this.createEditModelAndView(rendezvousForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(RendezvousForm rendezvousForm, final BindingResult binding) {
		ModelAndView result;
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.reconstruct(rendezvousForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(rendezvousForm);
		else
			try {
				this.rendezvousService.save(rendezvous);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(rendezvousForm,
						"rendezvous.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final RendezvousForm rendezvousForm, final BindingResult binding) {
		ModelAndView result;
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.reconstruct(rendezvousForm, binding);
		try {
			this.rendezvousService.delete(rendezvous);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(rendezvousForm, "rendezvous.commit.error");
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
	protected ModelAndView createEditModelAndView(final RendezvousForm rendezvousForm) {
		ModelAndView result;

		result = this.createEditModelAndView(rendezvousForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final RendezvousForm rendezvousForm, final String messageCode) {
		ModelAndView result;
		Collection<Rendezvous> similarOnes;
		/*
		if (rendezvous.getCreator().getCreatedRendezvouses().isEmpty())
			similarOnes = Collections.<Rendezvous> emptySet();
		else
			similarOnes = rendezvous.getCreator().getCreatedRendezvouses();
		 */
		
		similarOnes = rendezvousForm.getSimilarOnes();
		
		result = new ModelAndView("rendezvous/edit");
		result.addObject("rendezvousForm", rendezvousForm);
		result.addObject("similarRendezvouses", similarOnes);
		result.addObject("message", messageCode);

		return result;
	}

}
