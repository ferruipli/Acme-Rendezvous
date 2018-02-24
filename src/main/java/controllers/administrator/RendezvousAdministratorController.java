package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvousService;
import controllers.AbstractController;
import domain.Rendezvous;

@Controller
@RequestMapping("/rendezvous/administrator")
public class RendezvousAdministratorController extends AbstractController {

	// Services ------------------------------------------------------
	@Autowired
	private RendezvousService rendezvousService;
	
	// Constructors --------------------------------------------------
	public RendezvousAdministratorController() {
		super();
	}
	
	// Methods -------------------------------------------------------
	
	@RequestMapping(value="/remove", method = RequestMethod.GET)
	public ModelAndView remove(@RequestParam int rendezvousId) {
		ModelAndView result;
		Rendezvous rendezvous;
		
		rendezvous = this.rendezvousService.findOne(rendezvousId);
		 try {
			 this.rendezvousService.remove(rendezvous);
			 result = new ModelAndView("redirect:/rendezvous/list.do");
		 } catch (Throwable oops) {
			 result = this.createEditModelAndView(rendezvous, "rendezvous.commit.error");
		 }
		
		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Rendezvous rendezvous) {
		ModelAndView result;

		result = this.createEditModelAndView(rendezvous, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(Rendezvous rendezvous, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("rendezvous/edit");
		result.addObject("rendezvous", rendezvous);
		result.addObject("message", messageCode);

		return result;
	}
}
