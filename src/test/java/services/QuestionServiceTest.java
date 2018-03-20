package services;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Question;

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
	
	/*
	 * Requirement 21.1: Manage the questions that are associated with
	 *  a rendezvous that he or she is created previously.
	 */
	/* Caso de test positivo */
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
	
	/*
	 * Requirement 21.1: Manage the questions that are associated with
	 *  a rendezvous that he or she is created previously.
	 */
	
	/* Caso de test positivo */
	@Test
	public void testSave() {
		super.authenticate("user2");
		
		Question question,saved;
		Collection<Question> all;
		int rendezvousId;
		
		rendezvousId = this.findValidRendezvousID();
		
		question = this.questionService.create(rendezvousId);
		question.setStatement("Statement Test");
		
		saved = this.questionService.save(question);
		
		all = this.questionService.findAll();
		
		Assert.isTrue(all.contains(saved));
		Assert.isTrue(this.rendezvousService.findOrderedQuestionsByRendezvousId(rendezvousId).contains(saved));
		
		super.authenticate(null);
	}
	
	/* Caso de test negativo: Question::statement blank */
	@Test(expected = ConstraintViolationException.class)
	public void testInvalidSave1() {
		super.authenticate("user2");
		
		Question question,saved;
		Collection<Question> all;
		int rendezvousId;
		
		rendezvousId = this.findValidRendezvousID();
		
		question = this.questionService.create(rendezvousId);
		
		saved = this.questionService.save(question);
		this.questionService.flush();
		
		all = this.questionService.findAll();
		
		Assert.isTrue(all.contains(saved));
		Assert.isTrue(this.rendezvousService.findOrderedQuestionsByRendezvousId(rendezvousId).contains(saved));
		
		super.authenticate(null);
	}
	
	/* Caso de test negativo: Question is null */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidSave2() {
		super.authenticate("user2");
		
		Question question,saved;
		Collection<Question> all;
	
		question = null;
		
		saved = this.questionService.save(question);
		this.questionService.flush();
		
		all = this.questionService.findAll();
		
		Assert.isTrue(all.contains(saved));
		
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
	private int findValidRendezvousID() {
		int id;
		
		id = super.getEntityId("rendezvous4");
		
		return id;
	}
	
	/* Devuelve una cita que no se puede editar */
	private int findInvalidRendezvousID() {
		int id;
		
		id = super.getEntityId("rendezvous1");
		
		return id;
	}
	
}
