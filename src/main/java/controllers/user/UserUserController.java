/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;
import controllers.AbstractController;
import controllers.UserController;
import domain.User;

@Controller
@RequestMapping("/user/user")
public class UserUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private UserService		userService;

	// Controllers ------------------------------------------------------------

	@Autowired
	private UserController	userController;


	// Constructors -----------------------------------------------------------

	public UserUserController() {
		super();
	}

	// Profile ----------------------------------------------------------------

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView profile() {
		ModelAndView result;
		User user;

		user = this.userService.findByPrincipal();
		result = this.userController.profile(user.getId());

		return result;
	}

}
