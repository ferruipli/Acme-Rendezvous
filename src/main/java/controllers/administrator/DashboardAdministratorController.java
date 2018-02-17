package controllers.administrator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	// Constructors -----------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// Display ----------------------------------------------------------------
	@RequestMapping("/display")
	public ModelAndView display() {
		ModelAndView result;

		String requestURI = "dashboard/administrator/display.do";

		result = new ModelAndView("dashboard/display");

		result.addObject("requestURI",requestURI);
		
		return result;
	}

}
