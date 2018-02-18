package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
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
	private Collection<Rendezvous> 	attendedRendezvouses;
	private Collection<Comment> 	comments;
	private Collection<Question>	questions;
	private Collection<Answer>		answers;
	
	@NotNull
	@OneToMany(mappedBy="creator")
	public Collection<Rendezvous> getCreatedRendezvouses() {
		return createdRendezvouses;
	}
	
	public void setCreatedRendezvouses(Collection<Rendezvous> createdRendezvouses) {
		this.createdRendezvouses = createdRendezvouses;
	}
	
	@NotNull
	@ManyToMany(mappedBy="attendants")
	public Collection<Rendezvous> getAttendedRendezvouses() {
		return attendedRendezvouses;
	}
	
	public void setAttendedRendezvouses(Collection<Rendezvous> attendedRendezvouses) {
		this.attendedRendezvouses = attendedRendezvouses;
	}
	
	@NotNull
	@OneToMany(mappedBy="user")
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	@NotNull
	@OneToMany
	public Collection<Question> getQuestions() {
		return questions;
	}
	
	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
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
