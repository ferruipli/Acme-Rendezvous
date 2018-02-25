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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/legalTexts")
public class LegalTextsController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public LegalTextsController() {
		super();
	}

	// Terms and Conditions ---------------------------------------------------		

	@RequestMapping(value = "/termsAndConditions", method = RequestMethod.GET)
	public ModelAndView termsAndConditions() {
		ModelAndView result;

		result = new ModelAndView("legalTexts/termsAndConditions");

		return result;
	}

	@RequestMapping(value = "/legalData")
	public ModelAndView legalData() {
		ModelAndView result;

		result = new ModelAndView("legalTexts/legalData");

		return result;
	}
	@RequestMapping(value = "/cookies")
	public ModelAndView cookies() {
		ModelAndView result;

		result = new ModelAndView("legalTexts/cookies");

		return result;
	}
}
