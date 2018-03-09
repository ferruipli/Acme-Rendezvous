package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Comment() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private Date moment;
	private String text;
	private String urlPicture;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@SafeHtml
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@URL
	@SafeHtml
	public String getUrlPicture() {
		return this.urlPicture;
	}

	public void setUrlPicture(final String urlPicture) {
		this.urlPicture = urlPicture;
	}

	// Relationships ------------------------------------------------
	private User user;
	private Rendezvous rendezvous;
	private Collection<Comment> descendantComments;
	private Comment parentComment;

	@Valid
	@ManyToOne(optional = false)
	public Rendezvous getRendezvous() {
		return this.rendezvous;
	}

	public void setRendezvous(final Rendezvous rendezvous) {
		this.rendezvous = rendezvous;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@NotNull
	@OneToMany(mappedBy = "parentComment")
	public Collection<Comment> getDescendantComments() {
		return this.descendantComments;
	}

	public void setDescendantComments(
			final Collection<Comment> descendantComments) {
		this.descendantComments = descendantComments;
	}

	@Valid
	@ManyToOne(optional = true)
	public Comment getParentComment() {
		return this.parentComment;
	}

	public void setParentComment(final Comment parentComment) {
		this.parentComment = parentComment;
	}
}
