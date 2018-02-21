
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Rendezvous extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Rendezvous() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String				name;
	private String				description;
	private Date				moment;
	private GPS					gpsCoordinates;
	private boolean				finalMode;
	private boolean				isFlagged;
	private boolean				adultOnly;
	private String				urlPicture;

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Valid
	public GPS getGpsCoordinates() {
		return this.gpsCoordinates;
	}

	public void setGpsCoordinates(final GPS gpsCoordinates) {
		this.gpsCoordinates = gpsCoordinates;
	}

	public boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

	public boolean getIsFlagged() {
		return this.isFlagged;
	}

	public void setIsFlagged(final boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	public boolean getAdultOnly() {
		return this.adultOnly;
	}

	public void setAdultOnly(final boolean adultOnly) {
		this.adultOnly = adultOnly;
	}

	@URL
	public String getUrlPicture() {
		return this.urlPicture;
	}

	public void setUrlPicture(final String urlPicture) {
		this.urlPicture = urlPicture;
	}


	// Relationships ----------------------------------------------
	private User						creator;
	private Collection<User>			attendants;
	private Collection<RSVP>			reserves;
	private Collection<Comment>			comments;
	private Collection<Rendezvous>		similarOnes;
	private Collection<Announcement>	announcements;
	private Collection<Question>		questions;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getCreator() {
		return this.creator;
	}

	public void setCreator(final User creator) {
		this.creator = creator;
	}

	@NotNull
	@ManyToMany
	public Collection<User> getAttendants() {
		return this.attendants;
	}

	public void setAttendants(final Collection<User> attendants) {
		this.attendants = attendants;
	}
	
	@NotNull
	@OneToMany(mappedBy = "rendezvous")
	public Collection<RSVP> getReserves() {
		return this.reserves;
	}

	public void setReserves(final Collection<RSVP> reserves) {
		this.reserves = reserves;
	}

	@NotNull
	@OneToMany
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

	@NotNull
	@OneToMany
	public Collection<Rendezvous> getSimilarOnes() {
		return this.similarOnes;
	}

	public void setSimilarOnes(final Collection<Rendezvous> similarOnes) {
		this.similarOnes = similarOnes;
	}

	@NotNull
	@OneToMany
	public Collection<Announcement> getAnnouncements() {
		return this.announcements;
	}

	public void setAnnouncements(final Collection<Announcement> announcements) {
		this.announcements = announcements;
	}

	@NotNull
	@OneToMany
	public Collection<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(final Collection<Question> questions) {
		this.questions = questions;
	}
	
}
