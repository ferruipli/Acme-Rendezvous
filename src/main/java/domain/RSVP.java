package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class RSVP extends DomainEntity {

	// Constructors ----------------------------------
	public RSVP() {
		super();
	}
	
	// Attributes -------------------------------------
	
	// Relationships ----------------------------------
	private Rendezvous rendezvous;
	private Collection<Answer> answers;
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Rendezvous getRendezvous() {
		return rendezvous;
	}
	
	public void setRendezvous(Rendezvous rendezvous) {
		this.rendezvous = rendezvous;
	}
	
	@NotNull
	@OneToMany
	public Collection<Answer> getAnswers() {
		return answers;
	}
	
	public void setAnswers(Collection<Answer> answers) {
		this.answers = answers;
	}
	
}
