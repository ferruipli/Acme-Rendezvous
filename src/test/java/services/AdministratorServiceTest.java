
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	// Service under testing -----------------------------------

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private AnnouncementService	announcementService;

	@Autowired
	private QuestionService		questionService;

	@Autowired
	private CommentService		commentService;


	// Other services ------------------------------------------

	// Test ------------------------------------------------

	/**
	 * AcmeRendezvous Req 6.3.1
	 * The average and the standard deviation of rendezvous created per used.
	 * NOTE: This use case haven´t got negative test
	 */
	@Test
	public void testAvgSqrtRendezvousesPerUser() {
		super.authenticate("admin");
		this.rendezvousService.avgSqrtRendezvousesPerUser();
		super.unauthenticate();
	}

	/**
	 * AcmeRendezvous Req 6.3.2
	 * The ratio of users who have ever created a rendezvous
	 * versus the user who have never created any rendezvouses.
	 * NOTE: This use case haven´t got negative test
	 */
	@Test
	public void testRatioOfUsersWithRendezvousVsUsersWithoutRendezvous() {
		super.authenticate("admin");
		this.rendezvousService.ratioOfUsersWithRendezvousVsUsersWithoutRendezvous();
		super.unauthenticate();
	}

	/**
	 * AcmeRendezvous Req 6.3.3
	 * The average and the standard deviation of users per rendezvous.
	 * NOTE: This use case haven´t got negative test
	 */
	@Test
	public void testAvgSqrtUsersPerRendezvous() {
		super.authenticate("admin");
		this.rendezvousService.avgSqrtUsersPerRendezvous();
		super.unauthenticate();
	}
	/**
	 * AcmeRendezvous Req 6.3.4
	 * The average and standard deviation of rendezvouses that are RSVPd per user.
	 * NOTE: This use case haven´t got negative test
	 */
	@Test
	public void testAvgSqrtRendezvousesRSVPdPerUser() {
		super.authenticate("admin");
		this.rendezvousService.avgSqrtRendezvousesRSVPdPerUser();
		super.unauthenticate();
	}
	/**
	 * AcmeRendezvous Req 6.3.5
	 * The top-10 rendezvouses in terms of users who have RSVPd them.
	 * NOTE: This use case haven´t got negative test
	 */
	@Test
	public void testTop10RendezvousesRSVPd() {
		super.authenticate("admin");
		this.rendezvousService.top10RendezvousesRSVPd();
		super.unauthenticate();
	}

	/**
	 * AcmeRendezvous Req 17.2.1
	 * The average and the standard deviation of announcements per rendezvous.
	 * NOTE: This use case haven´t got negative test
	 */
	@Test
	public void testAvgSqrtAnnouncementsPerRendezvous() {
		super.authenticate("admin");
		this.announcementService.avgSqrtAnnouncementsPerRendezvous();
		super.unauthenticate();
	}

	/**
	 * AcmeRendezvous Req 17.2.2
	 * The rendezvouses that whose number of announcements is above 75% the average
	 * number of announcements per rendezvous.
	 * NOTE: This use case haven´t got negative test
	 */
	@Test
	public void testRendezvousesWhoseMoreThat75Announcements() {
		super.authenticate("admin");
		this.announcementService.rendezvousesWhoseMoreThat75Announcements();
		super.unauthenticate();
	}

	/**
	 * AcmeRendezvous Req 17.2.3
	 * The rendezvouses that are linked to a number of rendezvouses that
	 * is greater than the average plus 10%.
	 * NOTE: This use case haven´t got negative test
	 */
	@Test
	public void testRendezvousesLinkedPlus10() {
		super.authenticate("admin");
		this.rendezvousService.rendezvousesLinkedPlus10();
		super.unauthenticate();
	}

	/**
	 * AcmeRendezvous Req 22.1.1
	 * The average and the standard deviation of the number of questions per rendezvous.
	 * NOTE: This use case haven´t got negative test
	 */
	@Test
	public void testAvgSqrtQuestionsPerRendezvous() {
		super.authenticate("admin");
		this.questionService.avgSqrtQuestionsPerRendezvous();
		super.unauthenticate();
	}

	/**
	 * AcmeRendezvous Req 22.1.2
	 * The average and the standard deviation of the number of answers to the questions per rendezvous.
	 * NOTE: This use case haven´t got negative test
	 */
	@Test
	public void testAvgSqrtAnswersToQuestionsPerRendezvous() {
		super.authenticate("admin");
		this.questionService.avgSqrtAnswersToQuestionsPerRendezvous();
		super.unauthenticate();
	}

	/**
	 * AcmeRendezvous Req 22.1.3
	 * The average and the standard deviation of replies per comment.
	 * NOTE: This use case haven´t got negative test
	 */
	@Test
	public void testAvgSqrtRepliesPerComment() {
		super.authenticate("admin");
		this.commentService.avgSqrtRepliesPerComment();
		super.unauthenticate();
	}

}
