package controllers.administrator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import repositories.RendezvousRepository;
import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------
	RendezvousRepository rendezvousRepository;

	// Constructors -----------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// Display ----------------------------------------------------------------
	@RequestMapping("/display")
	public ModelAndView display() {
		ModelAndView result;
		Double[] avgSqrtRendezvousesPerUser;

		avgSqrtRendezvousesPerUser = this.rendezvousRepository.avgSqrtRendezvousesPerUser();
		String requestURI = "dashboard/administrator/display.do";
		

		result = new ModelAndView("dashboard/display");

		result.addObject("avgSqrtRendezvousesPerUser",avgSqrtRendezvousesPerUser);
		result.addObject("requestURI",requestURI);
		
		return result;
	}

}
