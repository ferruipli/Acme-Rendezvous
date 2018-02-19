package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvousService;

import controllers.AbstractController;

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
		
		result = new ModelAndView("redirect:list.do");
		
		 try {
			 this.rendezvousService.remove(rendezvousId);
		 } catch (Throwable oops) {
			 result.addObject("notice", "No se ha podido reservar la cita");
		 }
		
		return result;
	}
}
