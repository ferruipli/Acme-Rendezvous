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

import services.ActorService;
import services.ManagerService;
import services.UserService;
import domain.Actor;
import domain.Manager;
import domain.User;
import forms.RegistrationForm;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController{
	
	// Services
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ManagerService managerService;
	
	// Constructors
	
	public ActorController(){
		super();
	}
	
	
	// Profile
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView profile(@RequestParam(required = false) final Integer actorId) {
		ModelAndView result;
		Actor actor;

		if (actorId != null)
			actor = this.actorService.findOne(actorId);
		else
			actor = this.actorService.findByPrincipal();

		result = new ModelAndView("actor/profile");
		result.addObject("actor", actor);

		return result;
	}
	
	// Listing (Only for user) ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<User> users;

		users = this.userService.findAll();

		result = new ModelAndView("actor/list");
		result.addObject("requestURI", "actor/list.do");
		result.addObject("users", users);

		return result;
	}
	
	// Registration -----------------------------------------------------------

	@RequestMapping(value = "/registerUser", method = RequestMethod.GET)
	public ModelAndView createUser() {
		ModelAndView result;
		RegistrationForm registrationForm;
		String rol;
		
		rol = "User";
		registrationForm = new RegistrationForm();
		result = this.createModelAndView(registrationForm);
		result.addObject("rol", rol);

		return result;
	}
	
	@RequestMapping(value = "/registerManager", method = RequestMethod.GET)
	public ModelAndView createManager() {
		ModelAndView result;
		RegistrationForm registrationForm;
		String rol;

		rol = "Manager";
		registrationForm = new RegistrationForm();
		result = this.createModelAndView(registrationForm);
		result.addObject("rol", rol);

		return result;
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST, params = "register")
	public ModelAndView saveUser(final RegistrationForm registrationForm, final BindingResult binding) {
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
				result = this.createModelAndView(registrationForm,
						"actor.duplicated.username");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm,
						"actor.registration.error");
			}

		return result;
	}
	
	@RequestMapping(value = "/registerManager", method = RequestMethod.POST, params = "register")
	public ModelAndView saveManager(final RegistrationForm registrationForm, final BindingResult binding) {
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
				result = this.createModelAndView(registrationForm, "actor.duplicated.username");
			} catch (final Throwable oops) {
				result = this.createModelAndView(registrationForm, "actor.registration.error");
			}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createModelAndView(	final RegistrationForm registrationForm) {
		ModelAndView result;

		result = this.createModelAndView(registrationForm, null);

		return result;
	}

	protected ModelAndView createModelAndView(final RegistrationForm registrationForm, final String messageCode) {
		ModelAndView result;
		
		result = new ModelAndView("actor/register");
		result.addObject("registrationForm", registrationForm);
		result.addObject("message", messageCode);

		return result;
	}
}
