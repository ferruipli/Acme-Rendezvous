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
	private Rendezvous rendezvous;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Rendezvous getRendezvous() {
		return rendezvous;
	}

	public void setRendezvous(Rendezvous rendezvous) {
		this.rendezvous = rendezvous;
	}
	
}
