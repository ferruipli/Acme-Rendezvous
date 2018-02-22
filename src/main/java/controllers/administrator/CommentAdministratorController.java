package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;

import controllers.AbstractController;

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
	
	@RequestMapping(value="/remove", method = RequestMethod.GET)
	public ModelAndView remove(@RequestParam int commentId){
		ModelAndView result;
		
		result = new ModelAndView("redirect:/welcome/index.do");
		
		try{
			this.commentService.remove(commentId);
		} catch (Throwable oops) {
			result.addObject("notice", "No se ha podido eliminar el comentario");
		}
		
		return result;
	}

}
