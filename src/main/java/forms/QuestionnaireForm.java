
package forms;

import java.util.List;

import domain.Question;

public class QuestionnaireForm {

	// Attributes -------------------------------------------------------------

	private String			text;
	private List<Question>	questions;
	private List<String>	answers;
	private int				currentQuestionNumber;


	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public List<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(final List<Question> questions) {
		this.questions = questions;
	}

	public List<String> getAnswers() {
		return this.answers;
	}

	public void setAnswers(final List<String> answers) {
		this.answers = answers;
	}

	public int getCurrentQuestionNumber() {
		return this.currentQuestionNumber;
	}

	public void setCurrentQuestionNumber(final int currentQuestionNumber) {
		this.currentQuestionNumber = currentQuestionNumber;
	}

}
