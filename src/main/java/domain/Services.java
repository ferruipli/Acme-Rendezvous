
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Services extends DomainEntity {

	// Attributes ---------------------------
	private String	name;
	private String	description;
	private String	urlPicture;
	private boolean	isCancelled;
	private boolean	isRequested;


	// Constructors ------------------------
	public Services() {
		super();
	}

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

	@URL
	public String getUrlPicture() {
		return this.urlPicture;
	}

	public void setUrlPicture(final String urlPicture) {
		this.urlPicture = urlPicture;
	}

	public boolean getIsCancelled() {
		return this.isCancelled;
	}

	public void setIsCancelled(final boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public boolean getIsRequested() {
		return this.isRequested;
	}

	public void setIsRequested(final boolean isRequested) {
		this.isRequested = isRequested;
	}

	// Relationships ----------------------------------------

}
