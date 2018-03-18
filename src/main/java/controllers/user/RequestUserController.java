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

import services.RequestService;
import services.ServicesService;
import services.UserService;
import controllers.AbstractController;
import domain.Rendezvous;
import domain.Request;
import domain.Services;
import domain.User;

@Controller
@RequestMapping("/request/user")
public class RequestUserController extends AbstractController{

	// Services ---------------------------------------------------------------

	@Autowired
	private RequestService requestService;
	
	@Autowired
	private ServicesService servicesService;
	
	@Autowired
	private UserService		userService;
		
	// Constructors -----------------------------------------------------------
	public RequestUserController(){
		super();
	}
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int serviceId) {
		ModelAndView result;
		Request request;
		Services service;

		service = this.servicesService.findOne(serviceId);
		request = this.requestService.create();
		request.setService(service);
		
		result = this.createEditModelAndView(request);

		return result;
	}
	
	// Edition  ---------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Request request, final BindingResult binding) {
		ModelAndView result;
		Rendezvous rendezvous;
		
		rendezvous = request.getRendezvous();
		
		if (binding.hasErrors())
			result = this.createEditModelAndView(request);
		else
			try {
				this.requestService.save(request);
				result = new ModelAndView("redirect:/rendezvous/display.do?rendezvousId="+ String.valueOf(rendezvous.getId()));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(request, "request.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

		protected ModelAndView createEditModelAndView(final Request request) {
			ModelAndView result;

			result = this.createEditModelAndView(request, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(final Request request, final String messageCode) {
			ModelAndView result;
			User user;
			Collection<Rendezvous> rendezvouses;
			
			user = this.userService.findByPrincipal();
			rendezvouses = user.getCreatedRendezvouses();
			
			result = new ModelAndView("request/edit");
			result.addObject("request", request);
			result.addObject("rendezvouses", rendezvouses);
			result.addObject("message", messageCode);

			return result;
		}
		
}
