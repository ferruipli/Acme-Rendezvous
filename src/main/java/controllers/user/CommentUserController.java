package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import controllers.AbstractController;
import domain.Comment;

@Controller
@RequestMapping("/comment/user")
public class CommentUserController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private CommentService commentService;
	
	// Constructors -----------------------------------------------------------
	
	public CommentUserController(){
		super();
	}
	
	// CRUD methods -----------------------------------------------------------
	
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Comment comment;
		
		comment = this.commentService.create();
		
		result = this.createEditModelAndView(comment);
		
		return result;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.GET, params = "save")
	public ModelAndView save(@Valid Comment comment, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = this.createEditModelAndView(comment);
		} else {
			try {
				//this.commentService.save(comment);
				result = new ModelAndView("redirect:/rendezvous/user/list.do");	
			} catch (Throwable oops) {
				result = this.createEditModelAndView(comment, "comment.commit.error");
			}
		}
		
		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Comment comment) {
		ModelAndView result;
		
		result = createEditModelAndView(comment,null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Comment comment, String messageCode) {
		ModelAndView result;
		
		result = new ModelAndView("comment/edit");
		result.addObject("comment", comment);
		result.addObject("message", messageCode);
		
		
		return result;
	}

}
