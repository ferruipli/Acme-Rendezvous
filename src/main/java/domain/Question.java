package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

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
}
