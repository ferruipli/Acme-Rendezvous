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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;
import domain.User;
import forms.RegistrationForm;

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

	@RequestMapping(value = "/list", method = RequestMethod.GET)
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

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView profile(@RequestParam(required = false) final Integer userId) {
		ModelAndView result;
		User user;

		if (userId != null)
			user = this.userService.findOne(userId);
		else
			user = this.userService.findByPrincipal();

		result = new ModelAndView("user/profile");
		result.addObject("user", user);

		return result;
	}

	// Registration -----------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		RegistrationForm registrationForm;

		registrationForm = new RegistrationForm();
		result = this.createModelAndView(registrationForm);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "register")
	public ModelAndView save(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;
		User user;

		user = this.userService.reconstruct(registrationForm, binding);
		if (binding.hasErrors())
			result = this.createModelAndView(registrationForm);
		else
			try {
				this.userService.save(user);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (final DataIntegrityViolationException oops) {
				result = this.createModelAndView(registrationForm, "user.duplicated.username");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "user.registration.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createModelAndView(final RegistrationForm registrationForm) {
		ModelAndView result;

		result = this.createModelAndView(registrationForm, null);

		return result;
	}

	protected ModelAndView createModelAndView(final RegistrationForm registrationForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("user/register");
		result.addObject("registrationForm", registrationForm);
		result.addObject("message", messageCode);

		return result;
	}

}
