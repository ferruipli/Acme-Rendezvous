package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	// Constructors -----------------------------------------------------------
	// Hola Antonio
		public Actor() {
			super();
		}
	
	// Attributes -------------------------------------------------------------
		
		private String name;
		private String surname;
		private String postalAddress;
		private String phoneNumber;
		private String email;
		
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
		
		public void setsurname(String surname){
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
}
