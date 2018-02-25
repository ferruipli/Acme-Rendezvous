
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvousService;
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

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Rendezvous> rendezvouses;

		rendezvouses = this.rendezvousService.findAllAvailable();

		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("requestMapping", "rendezvous/list.do");

		return result;
	}

}
