package controllers.user;

import java.util.List;

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
import controllers.AbstractController;
import domain.Answer;
import domain.RSVP;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("/rsvp/user")
public class RSVPUserController extends AbstractController {
	
	// Services --------------------------------------------------------
	
	@Autowired
	private RSVPService rsvpService;
	
	@Autowired
	private RendezvousService rendezvousService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------
	
	public RSVPUserController(){
		super();
	}
	
	
	// CRUD methods ----------------------------------------------------
	
	@RequestMapping(value="/create", method= RequestMethod.GET)
	public ModelAndView create(@RequestParam int  rendezvousId){
		ModelAndView result;
		
		try {
		RSVP rsvp;
		Rendezvous rendezvous;
		
		rendezvous = this.rendezvousService.findOne(rendezvousId);
		
		rsvp = this.rsvpService.create(rendezvous);
		
		result = this.createEditModelAndView(rsvp);
		} catch (Throwable oops) {
			result = this.newModelAndView("redirect:/welcome/index.do");
		}
		return result;
		
	}
	
	@RequestMapping(value="/create", method = RequestMethod.POST, params="save")
	public ModelAndView save (@Valid RSVP rsvp, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = this.createEditModelAndView(rsvp);
		} else {
			try {
				this.rsvpService.save(rsvp);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(rsvp, "rendezvous.commit.error");
			}
		}
		
		return result;
			
	}
	
	@RequestMapping(value="/cancelRSVP", method = RequestMethod.POST, params = "cancelRSVP")
	public ModelAndView cancelRSVP(@Valid RSVP rsvp, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = this.createEditModelAndView(rsvp);
		} else {
			try {
				this.rsvpService.cancel(rsvp);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(rsvp, "rendezvous.commit.error");
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
		Rendezvous rendezvous;
		
		rendezvous = this.rendezvousService.findRendezvousByRSVPId(rsvp.getId());
		
		result = new ModelAndView("rendezvous/user/display.do?rendezvousId=" + rendezvous.getId());
		result.addObject("rsvp", rsvp);
		result.addObject("message", messageCode);
		
		
		return result;
	}
	
	public ModelAndView answers(List<Answer> answers, int rendezvousId){
		ModelAndView result;
		Rendezvous rendezvous;
		RSVP rsvp;
		User user;
		
		user = (User) this.actorService.findByPrincipal();
		rendezvous = this.rendezvousService.findOne(rendezvousId);
		
		rsvp = this.rsvpService.create(rendezvous);
		rsvp.setAnswers(answers);
		rsvp.setUser(user);
		
		try {
			this.rsvpService.save(rsvp);
			result = new ModelAndView("redirect:/rendezvous/display.do?rendezvousId=" + rendezvousId);
		}catch (Throwable oops) {
			result = this.createEditModelAndView(rsvp, "rendezvous.commit,error");
		}
		
		return result;
		
	}

}
