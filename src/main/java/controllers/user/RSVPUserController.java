package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RSVPService;
import controllers.AbstractController;
import domain.RSVP;
import domain.Rendezvous;

@Controller
@RequestMapping("/rsvp/user")
public class RSVPUserController extends AbstractController {
	
	// Services --------------------------------------------------------
	
	@Autowired
	private RSVPService rsvpService;
	
	// Constructors -----------------------------------------------------
	
	public RSVPUserController(){
		super();
	}
	
	
	// CRUD methods ----------------------------------------------------
	
	@RequestMapping(value="/create", method= RequestMethod.GET)
	public ModelAndView create(@Valid Rendezvous rendezvous){
		ModelAndView result;
		RSVP rsvp;
		
		rsvp = this.rsvpService.create(rendezvous);
		
		result = this.createEditModelAndView(rsvp);
		
		return result;
		
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.GET, params="save")
	public ModelAndView save (@Valid RSVP rsvp, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = this.createEditModelAndView(rsvp);
		} else {
			try {
				this.rsvpService.save(rsvp);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(rsvp, "rsvp.commit.error");
			}
		}
		
		return result;
			
	}
	
	@RequestMapping(value="/cancel", method = RequestMethod.POST, params = "cancel")
	public ModelAndView cancel(@Valid RSVP rsvp, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = this.createEditModelAndView(rsvp);
		} else {
			try {
				this.rsvpService.cancel(rsvp);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(rsvp, "rsvp.commit.error");
			}
		}
		
		return result;
	}
	
	// Arcillary methods ------------------------------------------------
	
	protected ModelAndView createEditModelAndView(RSVP rsvp) {
		ModelAndView result;
		
		result = createEditModelAndView(rsvp,null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(RSVP rsvp, String messageCode) {
		ModelAndView result;
		
		result = new ModelAndView("rsvp/edit");
		result.addObject("rsvp", rsvp);
		result.addObject("message", messageCode);
		
		
		return result;
	}

}
