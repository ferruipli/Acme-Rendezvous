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
	
	/* Caso de test negativo: Question is null */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidSave2() {
		super.authenticate("user3");
		
		Question question,saved;
		Collection<Question> all;
	
		question = null;
		
		saved = this.questionService.save(question);
		this.questionService.flush();
		
		all = this.questionService.findAll();
		
		Assert.isTrue(all.contains(saved));
		
		super.authenticate(null);
	}
	
	/* Caso de test positivo en el que se comprueba que una question
	 * se almacena correctamente. */
	@Test
	public void testSave() {
		super.authenticate("user3");
		
		Question question, saved;
		Collection<Question> all;
		
		all = this.questionService.findAll();
		question = this.findQuestion();
		
		Assert.isTrue(all.contains(question));
		
		question.setStatement("Edited Statement");
		saved = this.questionService.save(question);
		
		Assert.isTrue(all.contains(saved));
		
		super.authenticate(null);
	}
	
	@Test
	public void driver() {
		Object testingData[][] = {
				// Caso de test positivo
				{"user3", "Statement Test", super.getEntityId("rendezvous4"), null},
				///* Caso de test negativo: Question::statement blank */
				{"user3", "", super.getEntityId("rendezvous4"), IllegalArgumentException.class},
				/* Caso de test negativo: la rendezvous relacionada con la question que se quiere
					almacenar en la BD tiene Rendezvous::finalMode=true. */
				{"user2", "Statement 3", super.getEntityId("rendezvous1"), IllegalArgumentException.class},
				/* Test negativo donde la rendezvous relacionada con la question que se quiere
					almacenar en la BD tiene Rendezvous::isFlagged=true. */
				{"user2", "Statement 4", super.getEntityId("rendezvous1"), IllegalArgumentException.class},
				/*Test negativo donde el creador de la rendezvous relacionada con la question
					que se quiere almacenar no coincide con el principal. */
				{"user1", "Statement 5", super.getEntityId("rendezvous4"), IllegalArgumentException.class}
		};
		
		for (int i = 0; i < testingData.length; i++) {
			template((String)testingData[i][0],
					 (String)testingData[i][1],
					 (String)testingData[i][2],
					 (Class<?>) testingData[i][2]);
		}
	}
	
	protected void template(String username, String statement, String beanName, Class<?> expected) {
		Class<?> caught;
		Question question;
		int rendezvousId;
		
		caught = null;
		
		try {
			super.authenticate(username);
			rendezvousId = super.getEntityId(beanName);
			question = this.questionService.create(rendezvousId);
			question.setStatement(statement);
			this.questionService.save(question);
			this.questionService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		
		checkExceptions(expected,caught);
	}
	
	/* Devuelve una question de una cita que se puede editar. */
	private Question findQuestion() {
		Question result;
		int questionId;
		
		questionId = super.getEntityId("question2");
		result = this.questionService.findOne(questionId);
		
		return result;
	}
		
}
