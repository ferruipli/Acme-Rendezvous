package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

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
	public String getVAT() {
		return VAT;
	}

	public void setVAT(String vAT) {
		VAT = vAT;
	}
	
}
