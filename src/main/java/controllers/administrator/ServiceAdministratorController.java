package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ServicesService;

import controllers.AbstractController;
import domain.Services;

@Controller
@RequestMapping("/service/Administrator")
public class ServiceAdministratorController extends AbstractController {
	
	// Services -----------------------------------------------
	@Autowired
	private ServicesService servicesService;
	
	// Constructors -------------------------------------------
	public ServiceAdministratorController() {
		super();
	}
	
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam int serviceId) {
		ModelAndView result;
		Services service;
		
		service = this.servicesService.findOne(serviceId);
		result = new ModelAndView("redirect:welcome/index.jsp");
		
		try {
			this.servicesService.cancel(service);
		} catch (Throwable oops) {
			
		}
		
		return result;
	}
	
}
