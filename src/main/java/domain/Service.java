package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Service extends DomainEntity {

	// Attributes ---------------------------
	private String name;
	private String description;
	private String urlPicture;
	private boolean isCancelled;
	
	// Constructors ------------------------
	public Service() {
		super();
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@URL
	public String getUrlPicture() {
		return urlPicture;
	}

	public void setUrlPicture(String urlPicture) {
		this.urlPicture = urlPicture;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	
	// Relationships ----------------------------------------
	private Manager manager;
	
	@NotNull
	@ManyToOne(optional = false)
	public Manager getManager(){
		return this.manager;
	}
	
	public void setManager(Manager manager){
		this.manager = manager;
	}
}
