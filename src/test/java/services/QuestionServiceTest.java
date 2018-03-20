package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Question;
import domain.Rendezvous;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class QuestionServiceTest extends AbstractTest {

	// Service under testing ------------------------------
	@Autowired
	private QuestionService questionService;
	
	// Other services -------------------------------------
	@Autowired
	private RendezvousService rendezvousService;
	
	// Test -----------------------------------------------
	@Test
	public void testCreate() {
		Question question;
		int rendezvousId;
		
		rendezvousId = super.getEntityId("rendezvous2");
		question = this.questionService.create(rendezvousId);
		
		Assert.notNull(question);
		Assert.notNull(question.getAnswers());
		Assert.notNull(question.getRendezvous());
		Assert.isNull(question.getStatement());
	}
	
	@Test
	public void testSave() {
		super.authenticate("user2");
		
		super.authenticate(null);
	}
	
	/* Devuelve una question de una cita que se puede editar. */
	private Question findQuestion() {
		Question result;
		int questionId;
		
		questionId = super.getEntityId("question2");
		result = this.questionService.findOne(questionId);
		
		return result;
	}
	
	/* Devuelve una cita que se puede editar */
	private Rendezvous findValidRendezvous() {
		Rendezvous result;
		int rendezvousId;
		
		rendezvousId = super.getEntityId("rendezvous2");
		result = this.rendezvousService.findOne(rendezvousId);
		
		return result;
	}
	
	/* Devuelve una cita que no se puede editar */
	private Rendezvous findInvalidRendezvous() {
		Rendezvous result;
		int rendezvousId;
		
		rendezvousId = super.getEntityId("rendezvous1");
		result = this.rendezvousService.findOne(rendezvousId);
		
		return result;
	}
	
}
