/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;
import domain.User;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private UserService	userService;


	// Constructors -----------------------------------------------------------

	public UserController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<User> users;

		users = this.userService.findAll();

		result = new ModelAndView("user/list");
		result.addObject("requestURI", "user/list.do");
		result.addObject("users", users);

		return result;
	}

	// Profile ----------------------------------------------------------------

	@RequestMapping(value = "/profile")
	public ModelAndView profile(@RequestParam final int userId) {
		ModelAndView result;
		User user;

		user = this.userService.findOne(userId);

		result = new ModelAndView("user/profile");
		result.addObject("user", user);

		return result;
	}
}
