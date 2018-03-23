package controllers.user;

import java.util.Collection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import services.ServicesService;
import services.UserService;
import controllers.AbstractController;
import domain.CreditCard;
import domain.Rendezvous;
import domain.Request;
import domain.Services;
import domain.User;

@Controller
@RequestMapping("/request/user")
public class RequestUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RequestService requestService;

	@Autowired
	private ServicesService servicesService;

	@Autowired
	private UserService userService;

	// Constructors -----------------------------------------------------------
	public RequestUserController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int serviceId,
			@CookieValue(value = "CreditCard", defaultValue = "0") String cookie) {
		ModelAndView result;
		Request request;
		Services service;
		CreditCard creditCard;

		try {
			this.userService.findByPrincipal();
			service = this.servicesService.findOne(serviceId);
			request = this.requestService.create();
			request.setService(service);

			if (!cookie.equals("0")) {
				creditCard = this.requestService.reconstruct(cookie);
				request.setCreditCard(creditCard);
			}

			result = this.createEditModelAndView(request);
		} catch (Throwable oops) {
			result = new ModelAndView("reditect:../../service/list.do");
		}

		return result;
	}

	// Edition ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Request request,
			final BindingResult binding, HttpServletResponse response) {
		ModelAndView result;
		Rendezvous rendezvous;
		String cookie;

		rendezvous = request.getRendezvous();

		if (binding.hasErrors())
			result = this.createEditModelAndView(request);
		else
			try {
				this.requestService.save(request);
				result = new ModelAndView(
						"redirect:/rendezvous/display.do?rendezvousId="
								+ String.valueOf(rendezvous.getId()));
				cookie = request.getCreditCard().getHolderName() + "."
						+ request.getCreditCard().getBrandName() + "."
						+ request.getCreditCard().getNumber() + "."
						+ request.getCreditCard().getExpirationMonth() + "."
						+ request.getCreditCard().getExpirationYear() + "."
						+ request.getCreditCard().getCvvCode();
				response.addCookie(new Cookie("CreditCard", cookie));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(request,
						"request.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Request request) {
		ModelAndView result;

		result = this.createEditModelAndView(request, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Request request,
			final String messageCode) {
		ModelAndView result;
		User user;
		Collection<Rendezvous> rendezvouses;

		user = this.userService.findByPrincipal();
		rendezvouses = user.getCreatedRendezvouses();

		result = new ModelAndView("request/edit");
		result.addObject("request", request);
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("message", messageCode);

		return result;
	}

}
