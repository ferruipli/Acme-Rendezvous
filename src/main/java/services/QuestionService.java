package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.QuestionRepository;

@Service
@Transactional
public class QuestionService {

	// Managed repository --------------------------------------------------------------------
	@Autowired
	private QuestionRepository questionRepository;

	// Supporting services ------------------------------------------------------------------

	// Constructors ---------------------------------------------------------
	public QuestionService() {
		super();
	}

	// CRUD methods ---------------------------------------------------------

	// Other business methods ------------------------------------------------------------
	
	public Double[] avgSqrtQuestionsPerRendezvous(){
		Double[] result;
		
		result = this.questionRepository.avgSqrtQuestionsPerRendezvous();
		
		return result;
	}
	
	public Double[] avgSqrtAnswersToQuestionsPerRendezvous(){
		Double[] result;
		
		result = this.questionRepository.avgSqrtAnswersToQuestionsPerRendezvous();
		
		return result;
	}
}
