package forms;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import domain.GPS;
import domain.Rendezvous;

public class RendezvousForm {

	private int id;
	private int version;
	private String name;
	private String description;
	private Date moment;
	private boolean finalMode;
	private boolean adultOnly;
	private String urlPicture;
	private Collection<Rendezvous> similarOnes;
	private Double latitude;
	private Double longitude;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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
	
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
}
