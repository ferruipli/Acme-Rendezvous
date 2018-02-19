package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.Rendezvous;

import services.RendezvousService;

@Controller
@RequestMapping("/rendezvous")
public class RendezvousController extends AbstractController {

	// Services --------------------------------------------
	@Autowired
	private RendezvousService rendezvousService;
	
	// Constructors ----------------------------------------
	public RendezvousController() {
		super();
	}
	
	// CRUD methods ----------------------------------------
	
	@RequestMapping("/list")
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
