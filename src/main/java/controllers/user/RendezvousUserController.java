package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvousService;

import controllers.AbstractController;
import domain.Rendezvous;

@Controller
@RequestMapping("/rendezvous/user")
public class RendezvousUserController extends AbstractController {

	// Services --------------------------------------------------------
	@Autowired
	private RendezvousService rendezvousService;
	
	// Constructors -----------------------------------------------------
	public RendezvousUserController() {
		super();
	}
	
	// CRUD methods ----------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Rendezvous rendezvous;
		
		rendezvous = this.rendezvousService.create();
		
		result = this.createEditModelAndView(rendezvous);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int rendezvousId) {
		ModelAndView result;
		Rendezvous rendezvous;
		
		rendezvous = this.rendezvousService.findOne(rendezvousId);
		
		result = this.createEditModelAndView(rendezvous);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Rendezvous rendezvous, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(rendezvous);
		} else {
			try {
				this.rendezvousService.save(rendezvous);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(rendezvous,
						"rendezvous.commit.error");
			}
		}
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(Rendezvous rendezvous, BindingResult binding) {
		ModelAndView result;
		
		try {
			this.rendezvousService.delete(rendezvous);
			result = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			result = this.createEditModelAndView(rendezvous,
					"rendezvous.commit.error");
		}
		
		return result;
	}
	
	// Arcillary methods ------------------------------------------------
	protected ModelAndView createEditModelAndView(Rendezvous rendezvous) {
		ModelAndView result;
		
		result = createEditModelAndView(rendezvous,null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Rendezvous rendezvous, String messageCode) {
		ModelAndView result;
		Collection<Rendezvous> similarOnes;
		
		similarOnes = rendezvous.getSimilarOnes();
		
		result = new ModelAndView("rendezvous/edit");
		result.addObject("rendezvous", rendezvous);
		result.addObject("similarOnes", similarOnes);
		result.addObject("message", messageCode);
		
		return result;
	}
	
}
