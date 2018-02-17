package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	// Constructors -----------------------------------------------------------
		public Actor() {
			super();
		}
	
	// Attributes -------------------------------------------------------------
		
		private String name;
		private String surname;
		private String postalAddress;
		private String phoneNumber;
		private String email;
		private Date birthdate;
		
		@NotBlank
		public String getName(){
			return this.name;
		}
		
		public void setName(String name){
			this.name = name;
		}
		
		@NotBlank
		public String getSurname(){
			return this.surname;
		}
		
		public void setSurname(String surname){
			this.surname = surname;
		}
		
		public String getPostalAddress(){
			return this.postalAddress;
		}
		
		public void setPostalAddress(String postalAddress){
			this.postalAddress = postalAddress;
		}
		
		public String getPhoneNumber(){
			return this.phoneNumber;
		}
		
		public void setPhoneNumber(String phoneNumber){
			this.phoneNumber = phoneNumber;
		}
		
		@NotBlank
		@Email
		public String getEmail(){
			return this.email;
		}
		
		public void setEmail(String email){
			this.email = email;
		}
		
		@NotNull
		@Past
		@Temporal(TemporalType.DATE)
		@DateTimeFormat(pattern = "dd/MM/yyyy")
		public Date getBirthdate() {
			return birthdate;
		}

		public void setBirthdate(Date birthdate) {
			this.birthdate = birthdate;
		}
		
		// Relationships --------------------------------------
		private UserAccount userAccount;

		@NotNull
		@Valid
		@OneToOne(cascade = CascadeType.ALL, optional = false)
		public UserAccount getUserAccount() {
			return userAccount;
		}

		public void setUserAccount(UserAccount userAccount) {
			this.userAccount = userAccount;
		}
		
}
