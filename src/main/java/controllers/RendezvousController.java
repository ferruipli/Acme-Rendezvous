
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvousService;
import domain.Announcement;
import domain.Rendezvous;

@Controller
@RequestMapping("/rendezvous")
public class RendezvousController extends AbstractController {

	// Services --------------------------------------------
	@Autowired
	private RendezvousService	rendezvousService;

	// Constructors ----------------------------------------
	public RendezvousController() {
		super();
	}

	// CRUD methods ----------------------------------------

	@RequestMapping(value = "/listReserved", method = RequestMethod.GET)
	public ModelAndView listReserved(@RequestParam final int userId) {
		ModelAndView result;
		Collection<Rendezvous> rendezvouses;
		boolean isReserved;

		isReserved = true;
		rendezvouses = this.rendezvousService.findRendezvousesRSVPByUserId(userId);

		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("isReserved", isReserved);
		result.addObject("requestURI", "rendezvous/listReserved.do");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Rendezvous> rendezvouses;
		boolean isReserved;

		try {
			rendezvouses = this.rendezvousService.findAllAvailable();
		} catch (Throwable oops) {
			rendezvouses = this.rendezvousService.findAllAvailable2();
		}
		
		isReserved = false;

		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("isReserved", isReserved);
		result.addObject("requestURI", "rendezvous/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int rendezvousId) {
		ModelAndView result;
		Rendezvous rendezvous;
		Collection<Rendezvous> similarOnes;
		Collection<Announcement> announcements;

		try {
			rendezvous = this.rendezvousService.findOne(rendezvousId);
			
			if (rendezvous.getFinalMode()) {
				similarOnes = rendezvous.getSimilarOnes();
				announcements = rendezvous.getAnnouncements();
				
				result = new ModelAndView("rendezvous/display");
				result.addObject("rendezvous", rendezvous);
				result.addObject("similarOnes", similarOnes);
				result.addObject("announcements", announcements);
			} else {
				result = new ModelAndView("redirect:list.do");
			}
			
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}

}
