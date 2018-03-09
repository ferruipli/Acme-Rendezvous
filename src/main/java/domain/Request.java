package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity {

	// Attributes ----------------------------
	private String comment;
	private CreditCard creditCard;
	
	// Constructors --------------------------
	public Request() {
		super();
	}

	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	//Relationships
	
	private Service service;
	private Rendezvous rendezvous;
	
	@NotNull
	@ManyToOne(optional=false)
	public Service getService(){
		return this.service;
	}
	
	public void setService(Service service){
		this.service = service;
	}
	
	@NotNull
	@ManyToOne(optional=false)
	public Rendezvous getRendezvous(){
		return this.rendezvous;
	}
	
	public void setRendezvous(Rendezvous rendezvous){
		this.rendezvous = rendezvous;
	}
}
