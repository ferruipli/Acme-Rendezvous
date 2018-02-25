package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import controllers.AbstractController;
import domain.Comment;

@Controller
@RequestMapping("/comment/administrator")
public class CommentAdministratorController extends AbstractController {
	
	// Services ------------------------------------------------------
	
	@Autowired
	private CommentService commentService;
	
	// Constructors --------------------------------------------------
	
	public CommentAdministratorController(){
		super();
	}
	
	// Methods -------------------------------------------------------
	
	@RequestMapping(value="/list", method= RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Collection<Comment> comments;
		
		comments = this.commentService.findAll();
		
		result = new ModelAndView("comment/list");
		result.addObject("comments", comments);
		result.addObject("requestMapping", "comment/administrator/list.do");
		
		return result;
	}

	@RequestMapping(value="/remove", method = RequestMethod.GET)
	public ModelAndView remove(@RequestParam int commentId){
		ModelAndView result;
		Comment comment;
		
		result = new ModelAndView("redirect:/comment/administrator/list.do");
		
		try{
			comment = this.commentService.findOne(commentId);
			this.commentService.delete(comment);
		} catch (Throwable oops) {
			result.addObject("notice", "No se ha podido eliminar el comentario");
		}
		
		return result;
	}

}
