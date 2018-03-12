/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.manager;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import services.ServicesService;
import controllers.AbstractController;
import domain.Manager;
import domain.Services;

@Controller
@RequestMapping("/service/manager")
public class ServiceManagerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ServicesService	servicesService;

	@Autowired
	private ManagerService	managerService;


	// Constructors -----------------------------------------------------------

	public ServiceManagerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Manager principal;
		Collection<Services> services;
		boolean editable;

		principal = this.managerService.findByPrincipal();
		services = principal.getServices();
		editable = true;

		result = new ModelAndView("service/list");
		result.addObject("requestURI", "service/manager/list.do");
		result.addObject("editable", editable);
		result.addObject("services", services);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Services service;

		service = this.servicesService.create();
		result = this.createEditModelAndView(service);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int serviceId) {
		ModelAndView result;
		Services service;

		service = this.servicesService.findOne(serviceId);
		result = this.createEditModelAndView(service);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Services service, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(service);
		else
			try {
				this.servicesService.save(service);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(service, "service.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Services service, final BindingResult binding) {
		ModelAndView result;

		try {
			this.servicesService.delete(service);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(service, "service.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int serviceId) {
		Services service;
		ModelAndView result;

		service = this.servicesService.findOne(serviceId);
		result = this.delete(service, null);

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Services service) {
		ModelAndView result;

		result = this.createEditModelAndView(service, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Services service, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("service/edit");
		result.addObject("services", service);
		result.addObject("message", messageCode);

		return result;
	}

}
