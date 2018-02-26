
package domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
	private Collection<Rendezvous>	createdRendezvouses;
	private Collection<RSVP>		reserves;
	private Collection<Comment>		comments;


	@NotNull
	@OneToMany(mappedBy = "creator")
	public Collection<Rendezvous> getCreatedRendezvouses() {
		return this.createdRendezvouses;
	}

	public void setCreatedRendezvouses(final Collection<Rendezvous> createdRendezvouses) {
		this.createdRendezvouses = createdRendezvouses;
	}

	@NotNull
	@OneToMany(mappedBy = "user")
	public Collection<RSVP> getReserves() {
		return this.reserves;
	}

	public void setReserves(final Collection<RSVP> reserves) {
		this.reserves = reserves;
	}

	@NotNull
	@OneToMany(mappedBy = "user")
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}
	
	protected void removeRSVP(User user, RSVP rsvp) {
		Set<RSVP> aux;
		aux = new HashSet<>(user.getReserves());
		aux.remove(rsvp);
		user.setReserves(aux);
	}

}
