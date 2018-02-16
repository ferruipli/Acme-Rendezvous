package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Answer extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Answer() {
		super();
	}

	// Attributes -------------------------------------------------------------
	private String text;

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	// Relationships --------------------------------------------------------
	private Question question;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
}
