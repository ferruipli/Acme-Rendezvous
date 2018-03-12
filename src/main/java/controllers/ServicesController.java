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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ServicesService;
import controllers.AbstractController;
import domain.Services;

@Controller
@RequestMapping("/service")
public class ServicesController extends AbstractController {


	// Services ---------------------------------------------------------------

	@Autowired
	private ServicesService	servicesService;

	// Constructors -----------------------------------------------------------

	public ServicesController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Services> services;

		services = this.servicesService.availableServices();
			
		result = new ModelAndView("service/list");
		result.addObject("requestURI", "service/list.do");
		result.addObject("services", services);

		return result;
	}

}
