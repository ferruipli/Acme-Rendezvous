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

	private String name;
	private String description;
	private Date moment;
	private GPS gpsCoordinates;
	private boolean finalMode;
	private boolean isFlagged;
	private boolean adultOnly;
	private String urlPicture;

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@Valid
	public GPS getGpsCoordinates() {
		return this.gpsCoordinates;
	}

	public void setGpsCoordinates(GPS gpsCoordinates) {
		this.gpsCoordinates = gpsCoordinates;
	}

	public boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(boolean finalMode) {
		this.finalMode = finalMode;
	}

	public boolean getIsFlagged() {
		return isFlagged;
	}

	public void setIsFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	public boolean getAdultOnly() {
		return this.adultOnly;
	}

	public void setAdultOnly(boolean adultOnly) {
		this.adultOnly = adultOnly;
	}
	
	@URL
	public String getUrlPicture() {
		return urlPicture;
	}

	public void setUrlPicture(String urlPicture) {
		this.urlPicture = urlPicture;
	}
	
	// Relationships ----------------------------------------------
	private User 						creator;
	private Collection<RSVP>			reserves;
	private Collection<Comment>			comments;
	private Collection<Rendezvous> 		similarOnes;
	private Collection<Announcement>	announcements;
	private Collection<Question>		questions;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	@NotNull
	@OneToMany(mappedBy="rendezvous")
	public Collection<RSVP> getReserves() {
		return reserves;
	}

	public void setReserves(Collection<RSVP> reserves) {
		this.reserves = reserves;
	}

	@NotNull
	@OneToMany
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	@NotNull
	@OneToMany
	public Collection<Rendezvous> getSimilarOnes() {
		return similarOnes;
	}

	public void setSimilarOnes(Collection<Rendezvous> similarOnes) {
		this.similarOnes = similarOnes;
	}

	@NotNull
	@OneToMany
	public Collection<Announcement> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(Collection<Announcement> announcements) {
		this.announcements = announcements;
	}
	
	@NotNull
	@OneToMany
	public Collection<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}
	
}
