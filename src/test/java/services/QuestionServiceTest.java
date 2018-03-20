package services;

import java.util.Collection;

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
	
	// Test -----------------------------------------------
	
	/*
	 * Requirement 20.1: Manage the questions that are associated with
	 * a rendezvous that he or she is created previously.
	 */
	
	@Test
	public void driverDelete() {
		Object testingData[][] = {
				// Caso de test positivo
				{"user2", "question2", null},
				/* Test negativo donde la rendezvous relacionada con la question que se quiere
					eliminar de la BD tiene Rendezvous::finalMode=true. */
				{"user2","question1", IllegalArgumentException.class},
				/* Test negativo donde la rendezvous relacionada con la question que se quiere
					eliminar de la BD tiene Rendezvous::isFlagged=true.
				  */
				{"user2","question1", IllegalArgumentException.class},
				/* Test negativo donde el creador de la rendezvous relacionada con la question
				que se quiere borrar no coincide con el principal.
				 */
				{"user3","question2", IllegalArgumentException.class}
		};
		
		for (int i=0; i<testingData.length; i++) {
			templateDelete((String)testingData[i][0],
					 (String)testingData[i][1],
					 (Class<?>)testingData[i][2]);
		}
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deleteInvalid1() {
		super.authenticate("user3");
		
		int rendezvousId;
		Question question;
		
		rendezvousId = super.getEntityId("rendezvous4");
		question = this.questionService.create(rendezvousId);
		
		this.questionService.deleteEditableQuestion(question);
		
		super.authenticate(null);
	}
	
	/*Caso de test negativo: question is null */
	@Test(expected = IllegalArgumentException.class)
	public void deleteInvalid2() {
		super.authenticate("user3");
		
		Question question;
		
		question = null;
		
		this.questionService.deleteEditableQuestion(question);
		
		super.authenticate(null);
	}
	
	@Test
	public void driverSave() {
		Object testingData[][] = {
				// Caso de test positivo
				{"user3", "Statement Test", "rendezvous4", null},
				///* Caso de test negativo: Question::statement blank */
				{"user3", "", "rendezvous4", javax.validation.ConstraintViolationException.class},
				/* Caso de test negativo: la rendezvous relacionada con la question que se quiere
					almacenar en la BD tiene Rendezvous::finalMode=true. */
				{"user2", "Statement 3", "rendezvous1", IllegalArgumentException.class},
				/* Test negativo donde la rendezvous relacionada con la question que se quiere
					almacenar en la BD tiene Rendezvous::isFlagged=true. */
				{"user2", "Statement 4", "rendezvous1", IllegalArgumentException.class},
				/*Test negativo donde el creador de la rendezvous relacionada con la question
					que se quiere almacenar no coincide con el principal. */
				{"user1", "Statement 5", "rendezvous4", IllegalArgumentException.class},
		};
		
		for (int i = 0; i < testingData.length; i++) {
			templateSave((String)testingData[i][0],
					 (String)testingData[i][1],
					 (String)testingData[i][2],
					 (Class<?>) testingData[i][3]);
		}
	}
	
	/* Caso de test negativo: Question is null */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidSave() {
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
	
	protected void templateDelete(String username, String beanName, Class<?> expected) {
		Class<?> caught;
		Question question;
		int questionId;
		
		caught = null;
		
		try {
			super.startTransaction();
			super.authenticate(username);
			
			questionId = this.getEntityId(beanName);
			question = this.questionService.findOne(questionId);
			this.questionService.deleteEditableQuestion(question);
			
			super.authenticate(null);
			super.rollbackTransaction();
		} catch (Throwable oops) {
			caught = oops.getClass();
			
			super.rollbackTransaction();
		}
		
		checkExceptions(expected,caught);
	}
	
	protected void templateSave(String username, String statement, String beanName, Class<?> expected) {
		Class<?> caught;
		Question question;
		int rendezvousId;
		
		caught = null;
		
		try {
			super.startTransaction();
			super.authenticate(username);
			
			rendezvousId = super.getEntityId(beanName);
			question = this.questionService.create(rendezvousId);
			question.setStatement(statement);
			this.questionService.save(question);
			this.questionService.flush();
			
			super.unauthenticate();
			super.rollbackTransaction();
		} catch (Throwable oops) {
			caught = oops.getClass();
			
			super.rollbackTransaction();
		}
		
		checkExceptions(expected,caught);
	}
		
}
