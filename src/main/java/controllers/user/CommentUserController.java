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

import services.CommentService;
import services.RendezvousService;
import controllers.AbstractController;
import domain.Comment;
import domain.Rendezvous;

@Controller
@RequestMapping("/comment/user")
public class CommentUserController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private RendezvousService rendezvousService;
	
	// Constructors -----------------------------------------------------------
	
	public CommentUserController(){
		super();
	}
	
	// CRUD methods -----------------------------------------------------------
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(@RequestParam int rendezvousId){
		ModelAndView result;
		Collection<Comment> comments;
		Rendezvous rendezvous;
		
		rendezvous = this.rendezvousService.findOne(rendezvousId);
		comments = rendezvous.getComments();
		
		result = new ModelAndView("comment/list");
		result.addObject("comments", comments);
		result.addObject("requestMapping", "comment/user/list.do");
		
		return result;
		
	}
	
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int rendezvousId){
		ModelAndView result;
		try {
		Comment comment;	
		Rendezvous rendezvous;
		
		rendezvous = this.rendezvousService.findOne(rendezvousId);
		
		comment = this.commentService.create();
		comment.setRendezvous(rendezvous);
		result = this.createEditModelAndView(comment);
		} catch (Throwable oops) {
			result = this.newModelAndView("redirect:/welcome/index.do");
		}
		
		return result;
	}
	
	@RequestMapping(value="/create", method = RequestMethod.GET, params = "save")
	public ModelAndView save(@Valid Comment comment, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = this.createEditModelAndView(comment);
		} else {
			try {
				this.commentService.save(comment);
				result = new ModelAndView("redirect:/rendezvous/user/list.do");	
			} catch (Throwable oops) {
				result = this.createEditModelAndView(comment, "comment.commit.error");
			}
		}
		
		return result;
	}
	
//	@RequestMapping(value="/reply", method = RequestMethod.GET)
//	public ModelAndView reply(@RequestParam int commentId){
//		ModelAndView result;
//		try {
//
//		Comment comment;
//		Comment reply;
//		
//		comment = this.commentService.findOne(commentId);
//		
//		reply = this.commentService.create();
//		this.commentService.addReply(comment, reply);
//		
//		result = this.createEditModelAndView(reply);
//		} catch (Throwable oops) {
//			result = this.newModelAndView("redirect:/welcome/index.do");
//		}
//		
//		return result;
//	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Comment comment) {
		ModelAndView result;
		
		result = createEditModelAndView(comment);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Comment comment, String messageCode) {
		ModelAndView result;
		try {
		Collection<Rendezvous> allRendezvous;
		
		allRendezvous = this.rendezvousService.findAll();
				
		result = new ModelAndView("comment/create");
		result.addObject("comment", comment);
		result.addObject(allRendezvous);
		result.addObject("message", messageCode);
		
		} catch (Throwable oops) {
			result = this.newModelAndView("redirect: index.do");
		}
		return result;
	}

}
