package services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Comment;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class CommentService {

	// Managed repository --------------------------------------------------------------------
	@Autowired
	private CommentRepository commentRepository;

	// Supporting services ------------------------------------------------------------------
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private RendezvousService rendezvousService;

	// Constructors ---------------------------------------------------------
	public CommentService() {
		super();
	}

	// CRUD methods ---------------------------------------------------------
	
	public Comment create(){
		Comment result;
		Date moment;
		User user;
		Collection<Comment> descendantComments;
		
		user = (User) this.actorService.findByPrincipal();
		moment = new Date(System.currentTimeMillis()-1);
		result = new Comment();
		descendantComments = Collections.emptySet();
		
		result.setMoment(moment);
		result.setUser(user);
		result.setDescendantComments(descendantComments);
		
		return result;
	}
	
	public Comment findOne(int commentId){
		Assert.notNull(commentId != 0);
		
		Comment result;
		
		result = this.commentRepository.findOne(commentId);
		
		return result;
	}
	
	public Collection<Comment> findAll() {
		Collection<Comment> results;

		results = this.commentRepository.findAll();

		return results;
	}
	
	public void delete(Comment comment){
		Assert.notNull(comment);
		Assert.isTrue(comment.getId() != 0);
		
		Rendezvous rendezvous;
		
		rendezvous = this.rendezvousService.findRendezvousFromAComment(comment.getId());
		
		// Updating the comments about a rendezvous
		this.rendezvousService.removeComment(rendezvous, comment);
		
		this.commentRepository.delete(comment);
	}
	
	public void remove(Comment comment) {
		Assert.notNull(comment);
		Assert.isTrue(comment.getId() != 0);
		
		this.commentRepository.delete(comment);
	}
	
	public Comment save(Comment comment){
		this.checkByPrincipal(comment);
		Assert.notNull(comment);
		Assert.isTrue(comment.getDescendantComments().isEmpty());
		
		Comment result,parentComment;
		Rendezvous rendezvous;
		User user;
		Date currentMoment;
		
		currentMoment = new Date();
		Assert.isTrue(comment.getMoment().before(currentMoment));
		comment.setMoment(currentMoment);
		
		user = (User) this.actorService.findByPrincipal();
		comment.setUser(user);
		
		parentComment = comment.getParentComment();
		
		if(comment.getId()==0){
			result = this.commentRepository.save(comment);
		} else {
			if(comment.getParentComment()!=null){
				this.addDescendantComment(parentComment, comment);
				this.commentRepository.save(parentComment);
				result = this.commentRepository.save(comment);
			} else {
				result = this.commentRepository.save(comment);
			}
		}

		rendezvous = result.getRendezvous();
		Assert.isTrue(rendezvous.getAttendants().contains(user));
		
		this.rendezvousService.addComment(rendezvous, comment);
		
		return result;
	}

	// Other business methods ------------------------------------------------------------
	
	public void setParentComent(final Comment comment, final int parentCommentId) {
		Assert.isTrue(comment.getId() == 0);

		final Comment parentComment = this.findOne(parentCommentId);

		comment.setParentComment(parentComment);
	}

	public void addDescendantComment(final Comment comment, final Comment descendant) {
		Collection<Comment> aux;

		aux = new HashSet<>(comment.getDescendantComments());
		aux.add(descendant);
		comment.setDescendantComments(aux);
	}

	protected void removeDescendantComment(final Comment comment, final Comment descendant) {
		Collection<Comment> aux;

		aux = new HashSet<>(comment.getDescendantComments());
		aux.remove(descendant);
		comment.setDescendantComments(aux);
	}
	
	public Double[] avgSqrtRepliesPerComment(){
		Double[] result;
		
		result = this.commentRepository.avgSqrtRepliesPerComment();
		
		return result;
	}
	
	
	public Comment findCommentByReplyId(int replyId){
		Comment result;
		
		result = this.commentRepository.findCommentByReplyCommentId(replyId);
		
		return result;
		
	}
	
	private void checkByPrincipal(Comment comment) {
		Assert.notNull(comment);

		User user;

		user = (User) this.actorService.findByPrincipal();

		Assert.isTrue(comment.getUser().equals(user));
	}
	
}
