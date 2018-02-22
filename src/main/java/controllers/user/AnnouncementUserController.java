
package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import controllers.AbstractController;
import domain.Announcement;

@Controller
@RequestMapping("/announcement/user")
public class AnnouncementUserController extends AbstractController {

	// Services

	@Autowired
	private AnnouncementService	announcementService;


	// Constructors-------------------------------------

	public AnnouncementUserController() {
		super();
	}

	// Listing------------------------------------------

	// Creation---------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Announcement announcement;

		announcement = this.announcementService.create();
		result = this.createEditModelAndView(announcement);

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Announcement announcement, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(announcement);
		else
			try {
				this.announcementService.save(announcement);
				result = this.newModelAndView("redirect:list.do");
			} catch (final Throwable opps) {
				result = this.createEditModelAndView(announcement, "announcement.commit.error");
			}

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Announcement announcement) {
		ModelAndView result;

		result = this.createEditModelAndView(announcement, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Announcement announcement, final String messageCode) {
		ModelAndView result;

		result = this.newModelAndView("announcement/create");
		result.addObject("announcement", announcement);

		result.addObject("message", messageCode);

		return result;

	}

}
