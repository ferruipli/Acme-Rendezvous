package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import services.CommentService;
import services.QuestionService;
import services.RendezvousService;
import controllers.AbstractController;
import domain.Rendezvous;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private RendezvousService rendezvousService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnnouncementService announcementService;

	// Constructors -----------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// Display ----------------------------------------------------------------
	@RequestMapping("/display")
	public ModelAndView display() {
		ModelAndView result;
		
		Double[] avgSqrtRendezvousesPerUser;
		Double ratioOfUsersWithRendezvousVsUsersWithoutRendezvous;
		Double[] avgSqrtUsersPerRendezvous;
		Double[] avgSqrtRendezvousesRSVPdPerUser;
		Collection<Rendezvous> top10RendezvousesRSVPd;
		Collection<Rendezvous> rendezvousesLinkedPlus10;
		Double[] avgSqrtAnnouncementsPerRendezvous;
		Collection<Rendezvous> rendezvousesWhoseMoreThat75Announcements;
		Double[] avgSqrtQuestionsPerRendezvous;
		Double[] avgSqrtAnswersToQuestionsPerRendezvous;
		Double[] avgSqrtRepliesPerComment;
		
		avgSqrtRendezvousesPerUser = this.rendezvousService.avgSqrtRendezvousesPerUser();
		ratioOfUsersWithRendezvousVsUsersWithoutRendezvous = this.rendezvousService.ratioOfUsersWithRendezvousVsUsersWithoutRendezvous();
		avgSqrtUsersPerRendezvous = this.rendezvousService.avgSqrtUsersPerRendezvous();
		avgSqrtRendezvousesRSVPdPerUser = this.rendezvousService.avgSqrtRendezvousesRSVPdPerUser();
		top10RendezvousesRSVPd = this.rendezvousService.top10RendezvousesRSVPd();
		rendezvousesLinkedPlus10 = this.rendezvousService.rendezvousesLinkedPlus10();
		
		avgSqrtAnnouncementsPerRendezvous = this.announcementService.avgSqrtAnnouncementsPerRendezvous();
		rendezvousesWhoseMoreThat75Announcements = this.announcementService.rendezvousesWhoseMoreThat75Announcements();
		
		avgSqrtQuestionsPerRendezvous = this.questionService.avgSqrtQuestionsPerRendezvous();
		avgSqrtAnswersToQuestionsPerRendezvous = this.questionService.avgSqrtAnswersToQuestionsPerRendezvous();
		
		avgSqrtRepliesPerComment = this.commentService.avgSqrtRepliesPerComment();
		
		
		String requestURI = "dashboard/administrator/display.do";
		

		result = new ModelAndView("dashboard/display");

		result.addObject("avgSqrtRendezvousesPerUser",avgSqrtRendezvousesPerUser);
		result.addObject("ratioOfUsersWithRendezvousVsUsersWithoutRendezvous",ratioOfUsersWithRendezvousVsUsersWithoutRendezvous);
		result.addObject("avgSqrtUsersPerRendezvous",avgSqrtUsersPerRendezvous);
		result.addObject("avgSqrtRendezvousesRSVPdPerUser",avgSqrtRendezvousesRSVPdPerUser);
		result.addObject("top10RendezvousesRSVPd",top10RendezvousesRSVPd);
		result.addObject("rendezvousesLinkedPlus10",rendezvousesLinkedPlus10);
		result.addObject("avgSqrtAnnouncementsPerRendezvous",avgSqrtAnnouncementsPerRendezvous);
		result.addObject("rendezvousesWhoseMoreThat75Announcements",rendezvousesWhoseMoreThat75Announcements);
		result.addObject("avgSqrtQuestionsPerRendezvous",avgSqrtQuestionsPerRendezvous);
		result.addObject("avgSqrtAnswersToQuestionsPerRendezvous",avgSqrtAnswersToQuestionsPerRendezvous);
		result.addObject("avgSqrtRepliesPerComment", avgSqrtRepliesPerComment);
		
		result.addObject("requestURI",requestURI);
		
		return result;
	}

}
