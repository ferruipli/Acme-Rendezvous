package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

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
	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public Date getMoment(){
		return this.moment;
	}
	
	public void setMoment(Date moment){
		this.moment = moment;
	}
	
	public GPS getGpsCoordinates(){
		return this.gpsCoordinates;
	}
	
	public void setGpsCoordinates(GPS gpsCoordinates){
		this.gpsCoordinates = gpsCoordinates;
	}
	
	public boolean getFinalMode(){
		return this.finalMode;
	}
	
	public void setFinalMode(boolean finalMode){
		this.finalMode = finalMode;
	}
	
	public boolean getIsFlagged(){
		return this.isFlagged;
	}
	
	public void setIsFlagged(boolean isFlagged){
		this.isFlagged = isFlagged;
	}
	
	public boolean getIsCancelled(){
		return this.isCancelled;
	}
	
	public void setIsCancelled(boolean isCancelled){
		this.isCancelled = isCancelled;
	}
	
	public boolean getAdultOnly(){
		return this.adultOnly;
	}
	
	public void setAdultOnly(boolean adultOnly){
		this.adultOnly = adultOnly;
	}
}
