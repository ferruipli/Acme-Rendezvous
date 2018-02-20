package services;



import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Comment;
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
	//private RendezvousService rendezvousService;

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
	/*
	public void delete(Comment comment){
		Assert.isTrue(comment.getId() != 0);
		Assert.isTrue(LoginService.getPrincipal().equals("ADMINISTRATOR"));
		this.rendezvousService.removeComment(comment.g, comment);
		
		this.commentRepository.delete(comment);
		
	}*/
	
	public Comment save(Comment comment){
		Comment result;
		
		result = this.commentRepository.save(comment);
		
		return result;
	}

	// Other business methods ------------------------------------------------------------
	
	public Double[] avgSqrtRepliesPerComment(){
		Double[] result;
		
		result = this.commentRepository.avgSqrtRepliesPerComment();
		
		return result;
	}
}