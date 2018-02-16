package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
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
	private boolean isCancelled;
	private boolean adultOnly;

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
		return this.isFlagged;
	}

	public void setIsFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	public boolean getIsCancelled() {
		return this.isCancelled;
	}

	public void setIsCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public boolean getAdultOnly() {
		return this.adultOnly;
	}

	public void setAdultOnly(boolean adultOnly) {
		this.adultOnly = adultOnly;
	}
	
	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	
	// Relationships ----------------------------------------------
	private User 						creator;
	private Collection<User>			attendants;
	private Picture						picture;
	private Collection<Comment>			comments;
	private Collection<Rendezvous> 		similarOnes;
	private Collection<Announcement>	announcements;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	@NotEmpty
	@ManyToMany
	public Collection<User> getAttendants() {
		return attendants;
	}

	public void setAttendants(Collection<User> attendants) {
		this.attendants = attendants;
	}

	@Valid
	@OneToOne(optional = true)
	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
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
	
}
