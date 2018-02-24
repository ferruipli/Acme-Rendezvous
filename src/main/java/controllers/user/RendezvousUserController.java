package controllers.user;

import java.util.Collection;
import java.util.Collections;

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
import controllers.AbstractController;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("/rendezvous/user")
public class RendezvousUserController extends AbstractController {

	// Services --------------------------------------------------------
	@Autowired
	private RendezvousService rendezvousService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------
	public RendezvousUserController() {
		super();
	}
	
	
	
	// CRUD methods ----------------------------------------------------
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Collection<Rendezvous> rendezvouses;
		
		rendezvouses = this.rendezvousService.findAllAvailable();
		
		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("requestURI", "rendezvous/user/list.do");
		
		return result;
	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView listRSVP(){
		ModelAndView result;
		Collection<Rendezvous> rendezvouses;
		User user;
		
		user = (User) this.actorService.findByPrincipal();
		rendezvouses = this.rendezvousService.findRendezvousesRSVPByUserId(user.getId());
		
		result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
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
		
		if (rendezvous.getCreator().getCreatedRendezvouses().isEmpty()) {
			similarOnes = Collections.<Rendezvous>emptySet();
		} else {
			similarOnes = rendezvous.getCreator().getCreatedRendezvouses(); 
		}
		
		result = new ModelAndView("rendezvous/edit");
		result.addObject("rendezvous", rendezvous);
		result.addObject("similarRendezvouses", similarOnes);
		result.addObject("message", messageCode);
		
		return result;
	}
	
}
