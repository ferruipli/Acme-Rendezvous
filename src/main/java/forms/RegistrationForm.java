
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class RegistrationForm {

	// Attributes -------------------------------------------------------------

	private String			name;
	private String			surname;
	private String			email;
	private String			phoneNumber;
	private String			postalAddress;
	private Date			birthdate;
	private UserAccountForm	userAccount;
	private String			agreement;
	private String			confirmPassword;


	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPostalAddress() {
		return this.postalAddress;
	}

	public void setPostalAddress(final String postalAddress) {
		this.postalAddress = postalAddress;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(final Date birthdate) {
		this.birthdate = birthdate;
	}

	public UserAccountForm getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccountForm userAccount) {
		this.userAccount = userAccount;
	}

	public String getAgreement() {
		return this.agreement;
	}

	public void setAgreement(final String agreement) {
		this.agreement = agreement;
	}

	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
