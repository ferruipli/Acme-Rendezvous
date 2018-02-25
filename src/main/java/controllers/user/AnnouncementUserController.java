
package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import services.RendezvousService;
import services.UserService;
import controllers.AbstractController;
import domain.Announcement;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("/announcement/user")
public class AnnouncementUserController extends AbstractController {

	// Services

	@Autowired
	private AnnouncementService	announcementService;

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private UserService			userService;


	// Constructors-------------------------------------

	public AnnouncementUserController() {
		super();
	}

	// Listing------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			User user;
			Collection<Announcement> announcements;

			user = this.userService.findByPrincipal();
			announcements = this.announcementService.getAnnouncementsByUserRsvps(user);

			result = new ModelAndView("announcement/list");
			result.addObject(announcements);
			result.addObject("requestMapping", "rendezvous/list.do");
		} catch (final Exception e) {
			// TODO: handle exception
			result = this.newModelAndView("redirect:index/welcome.do");
		}

		return result;
	}

	// Creation---------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int rendezvousId) {
		ModelAndView result;
		try {
			Announcement announcement;
			Rendezvous rendezvous;

			rendezvous = this.rendezvousService.findOne(rendezvousId);

			announcement = this.announcementService.create();
			this.rendezvousService.addAnnouncement(rendezvous, announcement);

			result = this.createEditModelAndView(announcement);
		} catch (final Throwable opps) {
			result = this.newModelAndView("redirect=welcome/index.do");
		}

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
		try {
			Collection<Rendezvous> allRendezvous;

			allRendezvous = this.rendezvousService.findAll();

			result = this.newModelAndView("announcement/create");
			result.addObject("announcement", announcement);
			result.addObject(allRendezvous);

			result.addObject("message", messageCode);

		} catch (final Throwable oops) {
			result = this.newModelAndView("redirect: index.do");
		}

		return result;

	}

}
