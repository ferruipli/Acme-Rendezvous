package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

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

	@SafeHtml
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
	
	private Services service;
	private Rendezvous rendezvous;
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Services getService(){
		return this.service;
	}
	
	public void setService(Services service){
		this.service = service;
	}
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Rendezvous getRendezvous(){
		return this.rendezvous;
	}
	
	public void setRendezvous(Rendezvous rendezvous){
		this.rendezvous = rendezvous;
	}
}
