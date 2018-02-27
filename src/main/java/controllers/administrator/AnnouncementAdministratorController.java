
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import controllers.AbstractController;
import domain.Announcement;

@Controller
@RequestMapping("/announcement/administrator")
public class AnnouncementAdministratorController extends AbstractController {

	// Services

	@Autowired
	private AnnouncementService	announcementService;


	// Constructors-------------------------------------

	public AnnouncementAdministratorController() {
		super();
	}

	// Delete---------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Announcement announcement, final BindingResult binding) {
		ModelAndView result;

		try {
			this.announcementService.delete(announcement);
			result = this.newModelAndView("redirect:../../rendezvous/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(announcement, "announcement.commit.error");
		}

		return result;
	}

	// Display--------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int announcementId) {
		ModelAndView result;
		Announcement announcement;

		announcement = this.announcementService.findOne(announcementId);

		result = new ModelAndView("announcement/display");
		result.addObject("announcement", announcement);

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
