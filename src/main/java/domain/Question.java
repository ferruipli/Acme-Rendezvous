package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Question extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Question() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String statement;

	@NotBlank
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	// Relationships ----------------------------------------------------
	private Collection<Answer> answers;

	@NotNull
	@OneToMany
	public Collection<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Collection<Answer> answers) {
		this.answers = answers;
	}
	
}
