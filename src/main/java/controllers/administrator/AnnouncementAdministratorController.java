
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public ModelAndView remove(@RequestParam final int announcementId) {
		ModelAndView result;
		Announcement announcement;

		result = new ModelAndView("redirect:/rendezvous/list.do");

		try {
			announcement = this.announcementService.findOne(announcementId);
			this.announcementService.delete(announcement);
		} catch (final Throwable oops) {
			result.addObject("notice", "No se ha podido eliminar el anuncio");
		}

		return result;
	}
	// Display--------------------------------------------

	// Ancillary methods

}
