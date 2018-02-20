package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	// Constructors -----------------------------------------------------------

	public User() {
		super();
	}
	
	// Relationships ----------------------------------------------------------
	private Collection<Rendezvous> 	createdRendezvouses;
	private Collection<RSVP>		reserves;
	private Collection<Comment> 	comments;
	
	@NotNull
	@OneToMany(mappedBy="creator")
	public Collection<Rendezvous> getCreatedRendezvouses() {
		return createdRendezvouses;
	}
	
	public void setCreatedRendezvouses(Collection<Rendezvous> createdRendezvouses) {
		this.createdRendezvouses = createdRendezvouses;
	}
	
	@NotNull
	@OneToMany
	public Collection<RSVP> getReserves() {
		return reserves;
	}

	public void setReserves(Collection<RSVP> reserves) {
		this.reserves = reserves;
	}
	
	@NotNull
	@OneToMany(mappedBy="user")
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	
}
