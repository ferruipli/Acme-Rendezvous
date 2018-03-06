package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

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
		
		user = (User) this.actorService.findByPrincipal();
		moment = new Date(System.currentTimeMillis()-1);
		result = new Comment();
		
		result.setMoment(moment);
		result.setUser(user);
		result.setRepliedComments(Collections.<Comment> emptySet());
		
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
		Assert.notNull(comment);
		
		Comment result;
		Rendezvous rendezvous;
		User user;
		Date currentMoment;
		
		currentMoment = new Date();
		Assert.isTrue(comment.getMoment().before(currentMoment));
		user = (User) this.actorService.findByPrincipal();
		comment.setRepliedComments(Collections.<Comment> emptySet());
		comment.setUser(user);
		comment.setMoment(currentMoment);
		result = this.commentRepository.save(comment);
		rendezvous = result.getRendezvous();
		Assert.isTrue(rendezvous.getAttendants().contains(user));
		
		this.rendezvousService.addComment(rendezvous, comment);
		
		return result;
	}

	// Other business methods ------------------------------------------------------------
	
	public Double[] avgSqrtRepliesPerComment(){
		Double[] result;
		
		result = this.commentRepository.avgSqrtRepliesPerComment();
		
		return result;
	}
	
	public void remove(int commentId) {
		Assert.isTrue(commentId != 0);

		this.commentRepository.delete(commentId);
	}
	
	public void addReply(Comment comment, Comment reply){
		Collection<Comment> aux;
		
		aux = new ArrayList<>(comment.getRepliedComments());
		aux.add(reply);
		comment.setRepliedComments(aux);
	}
	
	public Comment findCommentByReplyId(int replyId){
		Comment result;
		
		result = this.commentRepository.findCommentByReplyCommentId(replyId);
		
		return result;
		
	}
	
}
