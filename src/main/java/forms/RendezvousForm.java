package forms;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import domain.Rendezvous;

public class RendezvousForm {

	private int id;
	private String name;
	private String description;
	private Date moment;
	private boolean finalMode;
	private boolean adultOnly;
	private String urlPicture;
	private Collection<Rendezvous> similarOnes;
	private GPSForm gpsCoordinates;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}
	
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	public boolean isFinalMode() {
		return finalMode;
	}
	
	public void setFinalMode(boolean finalMode) {
		this.finalMode = finalMode;
	}
	
	public boolean isAdultOnly() {
		return adultOnly;
	}
	
	public void setAdultOnly(boolean adultOnly) {
		this.adultOnly = adultOnly;
	}
	
	public String getUrlPicture() {
		return urlPicture;
	}
	
	public void setUrlPicture(String urlPicture) {
		this.urlPicture = urlPicture;
	}
	
	public Collection<Rendezvous> getSimilarOnes() {
		return similarOnes;
	}
	
	public void setSimilarOnes(Collection<Rendezvous> similarOnes) {
		this.similarOnes = similarOnes;
	}
	
	public GPSForm getGpsCoordinates() {
		return gpsCoordinates;
	}
	
	public void setGpsCoordinates(GPSForm gpsCoordinates) {
		this.gpsCoordinates = gpsCoordinates;
	}
	
}
