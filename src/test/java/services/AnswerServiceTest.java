package services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Answer;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AnswerServiceTest extends AbstractTest {

	// SUT ---------------------------------------------------------------
	@Autowired
	private AnswerService answerService;
	
	// Other services ----------------------------------------------------
	
	// Test --------------------------------------------------------------
	
	/*
	 * Requirement 21.2:Answer the questions that are associated with
	 *  a rendezvous that he or she is RSVPing now.
	 */
	
	@Test
	public void testCreate() {
		Answer answer;
		int questionId;
		
		questionId = super.getEntityId("question1");
		
		answer = this.answerService.create(questionId);
		
		Assert.notNull(answer);
		Assert.notNull(answer.getQuestion());
		Assert.isNull(answer.getText());
	}
	
	/* Caso de test positivo en el que se insertan varias respuestas a la vez */
	@Test
	public void testSaves() {
		super.authenticate("user3");
		
		List<Answer> answers;
		Answer a1, a2;
		int questionId;
		
		answers = new ArrayList<>();
		
		questionId = super.getEntityId("question5");
		a1 = this.answerService.create(questionId);
		a1.setText("Text 1");
		
		questionId = super.getEntityId("question6");
		a2 = this.answerService.create(questionId);
		a2.setText("Text 2");
		
		answers.add(a1);
		answers.add(a2);
		
		this.answerService.save(answers);
		
		super.authenticate(null);
	}
	
	// Caso de test negativo: Answer null
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidSave1() {
		super.authenticate("user3");
		
		Answer answer;
		
		answer = null;
		
		this.answerService.save(answer);
		this.answerService.flush();
		
		super.authenticate(null);
	}
	
	@Test
	public void driver() {
		Object testingData[][] = {
				// Caso de test positivo
				{"user3", "question5", "text Test 1", null},
				// Caso de test negativo: Answer::text blank
				{"user3", "question6", "", javax.validation.ConstraintViolationException.class},
				// Caso de test negativo: Answer::text safeHtml
				{"user1", "question5", "<script> Hacking </script>", javax.validation.ConstraintViolationException.class},
				// Caso de test negativo: Answer::question::rendezvous finalMode=false
				{"user1", "question2", "Text Test 4", IllegalArgumentException.class},
				// Caso de test negativo: Answer::question::rendezvous isFlagged=true
				{"user1", "question1", "Text Test 5", IllegalArgumentException.class}
		};
		
		for (int i=0;i<testingData.length; i++) {
			template((String)testingData[i][0],
					 (String)testingData[i][1],
					 (String)testingData[i][2],
					 (Class<?>)testingData[i][3]
					);
		}
		
	}
	
	protected void template(String username, String beanName, String text, Class<?> expected) {
		Class<?> caught;
		Answer answer;
		int questionId;
		
		caught = null;
		
		try {
			super.startTransaction();
			super.authenticate(username);
			
			questionId = super.getEntityId(beanName);
			answer = this.answerService.create(questionId);
			answer.setText(text);
			
			this.answerService.save(answer);
			this.answerService.flush();
			
			super.authenticate(null);
			super.rollbackTransaction();
		} catch (Throwable oops) {
			caught = oops.getClass();
			
			super.rollbackTransaction();
		}
		
		super.checkExceptions(expected, caught);
	}
	
}
