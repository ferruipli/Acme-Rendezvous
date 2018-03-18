
package services;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Comment;
import domain.Rendezvous;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private CommentService		commentService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private RendezvousService	rendezvousService;


	// Tests ------------------------------------------------------------------

	/**
	 * Acme Rendezvous 1.0:
	 * An actor who is authenticated as a user must be able to:
	 * Comment on the rendezvouses that he or she's RSVPd.
	 * REMARK: user comment a rendezvous that he has RSVPd.
	 * */
	@Test
	public void commentRsvpdRendezvous() {
		super.authenticate("user1");

		Rendezvous rendezvous;
		Comment comment;

		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous1"));
		comment = this.commentService.create();
		comment.setRendezvous(rendezvous);
		this.commentService.save(comment);

		super.unauthenticate();
	}

	/**
	 * Acme Rendezvous 1.0:
	 * An actor who is authenticated as a user must be able to:
	 * Comment on the rendezvouses that he or she's RSVPd.
	 * REMARK: user comment a rendezvous that he has not RSVPd.
	 * */
	@Test(expected = IllegalArgumentException.class)
	public void commentNonRsvpdRendezvous() {
		super.authenticate("user1");

		Rendezvous rendezvous;
		Comment comment;

		rendezvous = this.rendezvousService.findOne(super.getEntityId("rendezvous3"));
		comment = this.commentService.create();
		comment.setRendezvous(rendezvous);
		this.commentService.save(comment);
		this.commentService.flush();

		super.unauthenticate();
	}

	/**
	 * Acme Rendezvous 1.0:
	 * An actor who is authenticated as an administrator must be able to:
	 * Remove a comment that he or she thinks is inappropriate.
	 * REMARK: administrator remove an existent comment.
	 * */
	@Test
	public void adminRemoveComment() {
		super.authenticate("admin");

		Comment comment;

		comment = this.commentService.findOne(super.getEntityId("comment1"));
		this.commentService.delete(comment);
		this.commentService.flush();

		super.unauthenticate();
	}

	/**
	 * Acme Rendezvous 1.0:
	 * An actor who is authenticated as an administrator must be able to:
	 * Remove a comment that he or she thinks is inappropriate.
	 * REMARK: administrator remove a non existent comment.
	 * */
	@Test(expected = IllegalArgumentException.class)
	public void managerRemoveComment() {
		Comment comment;

		super.authenticate("user1");
		comment = this.commentService.create();
		super.unauthenticate();

		super.authenticate("admin");
		this.commentService.delete(comment);
		this.commentService.flush();
		super.unauthenticate();
	}

	/**
	 * Acme Rendezvous 1.0:
	 * In addition to writing a comment from scratch, a user may reply to a
	 * comment.
	 * REMARK: user replies a comment.
	 */
	@Test
	public void userReplyComment() {
		super.authenticate("user1");

		Comment reply, parentComment;

		parentComment = this.commentService.findOne(super.getEntityId("comment1"));
		reply = this.commentService.create();
		reply.setMoment(LocalDate.now().toDate());
		reply.setText("prueba");
		reply.setRendezvous(parentComment.getRendezvous());
		reply.setParentComment(parentComment);
		this.commentService.save(reply);
		this.commentService.flush();

		super.unauthenticate();
	}

	/**
	 * Acme Rendezvous 1.0:
	 * In addition to writing a comment from scratch, a user may reply to a
	 * comment.
	 * REMARK: administrator replies a comment.
	 */
	@Test(expected = ClassCastException.class)
	public void adminReplyComment() {
		super.authenticate("admin");

		Comment reply, parentComment;

		parentComment = this.commentService.findOne(super.getEntityId("comment1"));
		reply = this.commentService.create();
		reply.setMoment(LocalDate.now().toDate());
		reply.setText("prueba");
		reply.setRendezvous(parentComment.getRendezvous());
		reply.setParentComment(parentComment);
		this.commentService.save(reply);
		this.commentService.flush();

		super.unauthenticate();
	}

}
