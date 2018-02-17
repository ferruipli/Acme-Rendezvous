package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CommentRepository;

@Service
@Transactional
public class CommentService {

	// Managed repository --------------------------------------------------------------------
	@Autowired
	private CommentRepository commentRepository;

	// Supporting services ------------------------------------------------------------------

	// Constructors ---------------------------------------------------------
	public CommentService() {
		super();
	}

	// CRUD methods ---------------------------------------------------------

	// Other business methods ------------------------------------------------------------
	
	public Double[] avgSqrtRepliesPerComment(){
		Double[] result;
		
		result = this.commentRepository.avgSqrtRepliesPerComment();
		
		return result;
	}
}
