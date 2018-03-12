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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import domain.Manager;
import forms.RegistrationForm;

@Controller
@RequestMapping("/manager")
public class ManagerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ManagerService	managerService;


	// Constructors -----------------------------------------------------------

	public ManagerController() {
		super();
	}


	// Profile ----------------------------------------------------------------

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView profile(@RequestParam(required = false) final Integer managerId) {
		ModelAndView result;
		Manager manager;

		if (managerId != null)
			manager = this.managerService.findOne(managerId);
		else
			manager = this.managerService.findByPrincipal();

		result = new ModelAndView("manager/profile");
		result.addObject("manager", manager);

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
		Manager manager;

		manager = this.managerService.reconstruct(registrationForm, binding);
		if (binding.hasErrors())
			result = this.createModelAndView(registrationForm);
		else
			try {
				this.managerService.save(manager);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (final DataIntegrityViolationException oops) {
				result = this.createModelAndView(registrationForm, "manager.duplicated.username");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "manager.registration.error");
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

		result = new ModelAndView("manager/register");
		result.addObject("registrationForm", registrationForm);
		result.addObject("message", messageCode);

		return result;
	}

}
