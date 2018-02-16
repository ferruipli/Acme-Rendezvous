package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Picture extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Picture() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String url;

	@NotBlank
	@URL
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
