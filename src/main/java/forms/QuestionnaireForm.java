
package forms;

import java.util.List;

import domain.Question;
import domain.Rendezvous;

public class QuestionnaireForm {

	// Attributes -------------------------------------------------------------

	private Rendezvous		rendezvous;
	private List<String>	answers;
	private List<Question>	questions;


	public Rendezvous getRendezvous() {
		return this.rendezvous;
	}

	public void setRendezvous(final Rendezvous rendezvous) {
		this.rendezvous = rendezvous;
	}

	public List<String> getAnswers() {
		return this.answers;
	}

	public void setAnswers(final List<String> answers) {
		this.answers = answers;
	}

	public List<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(final List<Question> questions) {
		this.questions = questions;
	}

}
