
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AnswerRepository;
import domain.Answer;

@Service
@Transactional
public class AnswerService {

	// Managed repository ---------------------------------------------------

	@Autowired
	private AnswerRepository	answerRepository;


	// Supporting services --------------------------------------------------

	// Constructors ---------------------------------------------------------

	public AnswerService() {
		super();
	}

	// CRUD methods ---------------------------------------------------------

	// Other business methods -----------------------------------------------

	public List<Answer> findOrderedAnswersByRendezvousId(final int rendezvousId) {
		List<Answer> result;

		result = new ArrayList<>(this.answerRepository.findOrderedAnswersByRendezvousId(rendezvousId));
		Assert.notNull(result);

		return result;
	}

}
