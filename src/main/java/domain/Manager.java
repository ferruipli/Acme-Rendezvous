package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	// Attributes --------------------------------------------
	private String VAT;

	// Constructors -----------------------------------------
	public Manager() {
		super();
	}

	@NotBlank
	@Pattern(regexp = "[a-zA-Z0-9-]+")
	@SafeHtml
	public String getVAT() {
		return VAT;
	}

	public void setVAT(String vAT) {
		VAT = vAT;
	}

	// Relationships -----------------------------------------
	private Collection<Services> services;

	@NotNull
	@OneToMany
	public Collection<Services> getServices() {
		return this.services;
	}

	public void setServices(Collection<Services> services) {
		this.services = services;
	}
}
