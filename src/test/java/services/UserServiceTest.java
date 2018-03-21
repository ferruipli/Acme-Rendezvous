package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Rendezvous;
import domain.User;

import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UserServiceTest extends AbstractTest {
	
	// Services under testing -----------------------------------
	@Autowired
	private UserService userService;
	
	// Supporting test ------------------------------------------
	@Autowired
	private RendezvousService rendezvousService;
	
	// Test -------------------------------------------------
	
	/*
	 * Requirement 20.1: Display information about the users who have
	 * RSVPd a rendezvous, which, in turn, must show their answers
	 * to the questions that the creator has registered.
	 */
	
	/*
	 * Con este caso de test, la intención es comprobar que un usuario no
	 * autenticado puede acceder a los asistentes de una cita. Para mostrar las
	 * respuestas de las cuestiones de una cita hay que ir al test de Rendezvous.
	 * Aquí no.
	 */
	@Test
	public void testFindAttendants() {
		super.authenticate(null);
		
		Collection<User> attendants;
		int rendezvousId;
		Rendezvous rendezvous;
		
		rendezvousId = super.getEntityId("rendezvous1");
		rendezvous = this.rendezvousService.findOne(rendezvousId);
		attendants = rendezvous.getAttendants();
		
		Assert.notNull(attendants);
		Assert.isTrue(attendants.size() > 0);
		
		super.authenticate(null);
	}
	
	/*
	 * Requirements 4.2: List the users of the system and navigate to their profiles,
	 *  which include personal data and the list of rendezvouses that
	 *   they have attended or are going to attend.
	 */
	// Caso de test positivo
	@Test
	public void testFindAll() {
		super.authenticate(null);
		
		Collection<User> all;
		all = this.userService.findAll();
		Assert.isTrue(all.size() > 0);
		
		super.authenticate(null);
	}
	
	/*
	 * Caso de test positivo. Se devuelve el usuario con el id seleccionado.
	 */
	@Test
	public void testFindOne() {
		super.authenticate(null);
		
		User user;
		int userId;
		
		userId = super.getEntityId("user1");
		user = this.userService.findOne(userId);
		
		Assert.notNull(user);
	
		super.authenticate(null);
	}
	
	/*
	 * Se trata de un caso de test negativo ya que no existe ningún
	 * user con este identificador
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNotFindOne() {
		super.authenticate(null);
		
		User user;
		int userId;
		
		userId = super.getEntityId("user10");
		user = this.userService.findOne(userId);
		
		Assert.notNull(user);
		Assert.notNull(user.getCreatedRendezvouses());
	
		super.authenticate(null);
	}
	
	/*
	 * Requirement 4.1: Register to the system as a user.
	 * 	Ninguna excepción debería saltar, es un caso de test positivo
	 */
	@Test
	public void testCreate() {
		User user;
	
		user = this.userService.create();
		
		Assert.notNull(user);
		Assert.notNull(user.getComments());
		Assert.notNull(user.getCreatedRendezvouses());
		Assert.notNull(user.getReserves());
		Assert.notNull(user.getUserAccount());
		Assert.isNull(user.getName());
		Assert.isNull(user.getSurname());
		Assert.isNull(user.getPostalAddress());
		Assert.isNull(user.getPhoneNumber());
		Assert.isNull(user.getEmail());
		Assert.isNull(user.getBirthdate());
	}
	
	/*
	 * Requirement 4.1: Register to the system as a user.
	 */
	
	@Test
	public void driver() {
		Object testingData[][] = {
				// Test positivo
				{"Name Test 1", "Surname Test 1", "PostalAddress Test 1",
						"+34 955060405", "alvarorex@gmail.com", this.getDate(1997, 10, 30),
						"usernameTest1", "passwordTest1", null},
				// User::userAccount::password size=5
				{"Name Test 2", "Surname Test 2", "PostalAddress Test 2",
						"+34 955060405", "alvarorex@gmail.com", this.getDate(1997, 10, 30), 
						"usernameTest2", "pass5", null},
				// User::userAccount::password size=6
				{"Name Test 3", "Surname Test 3", "PostalAddress Test 3",
						"+34 955060405", "alvarorex@gmail.com", this.getDate(1997, 10, 30), 
						"usernameTest3", "pass56", null},
				// User::userAccount::password size=14
				{"Name Test 4", "Surname Test 4", "PostalAddress Test 4",
						"+34 955060405", "alvarorex@gmail.com", this.getDate(1997, 10, 30), 
						"usernameTest4", "passwordTest17", null},
				// User::userAccount::password size=31
				{"Name Test 5", "Surname Test 5", "PostalAddress Test 5",
						"+34 955060405", "alvarorex@gmail.com", this.getDate(1997, 10, 30), 
						"usernameTest5", "passwordTest17161415090807YYtT1", null},
				// User::userAccount::password size=32
				{"Name Test 6", "Surname Test 6", "PostalAddress Test 6",
						"+34 955060405", "alvarorex@gmail.com", this.getDate(1997, 10, 30), 
						"usernameTest6", "passwordTest17161415090807YYtT32", null},
				// User::postalAddress optional attribute
				{"Name Test 7", "Surname Test 7", "",
						"+34 955060405", "alvarogoles@gmail.com", this.getDate(2000, 10, 30),
						"usernameTest7", "passwordTest12", null},
				// User::phoneNumber optional attribute
				{"Name Test 8", "Surname Test 8", "Postal Address 8",
						"", "alvarogoles@gmail.com", this.getDate(1999, 10, 30),
						"username8", "password13", null},
				// User::name blank
				{"", "Surname Test 9", "PostalAddress Test 9",
						"+34 955060405", "alvarorex@gmail.com", this.getDate(1997, 10, 30),
						"usernameTest9", "passwordTest9", javax.validation.ConstraintViolationException.class},
				// User::name safeHtml
				{"<script> Hacking Test </script>", "Surname Test 10", "PostalAddress Test 10",
						"+34 955060405", "alvarorex@gmail.com", this.getDate(1997, 10, 30),
						"usernameTest10", "passwordTest10", org.springframework.dao.DataIntegrityViolationException.class},
				// User::surname blank
				{"Name Test 11", "", "PostalAddress Test 11",
						"+34 955060405", "alvarorex@gmail.com", this.getDate(1997, 10, 30),
						"usernameTest11", "passwordTest11", org.springframework.dao.DataIntegrityViolationException.class},
				// User::surname safeHtml
				{"Name Test 12", "<script> Hacking Test </script>", "PostalAddress Test 12",
						"+34 955060405", "alvarorex@gmail.com", this.getDate(1997, 10, 30),
						"usernameTest12", "passwordTest12", org.springframework.dao.DataIntegrityViolationException.class},
				// User::email blank
				{"Name Test 13", "Surname Test 13", "PostalAddress Test 13",
						"+34 955060405", "", this.getDate(1997, 10, 30),
						"usernameTest13", "passwordTest13", org.springframework.dao.DataIntegrityViolationException.class},
				// User::email safeHtml
				{"Name Test 14", "Surname Test 14", "PostalAddress Test 14",
						"+34 955060405", "<script> Hacking Test </script>", this.getDate(1997, 10, 30),
						"usernameTest14", "passwordTest14", org.springframework.dao.DataIntegrityViolationException.class},
				// User::phoneNumber pattern
				{"Name Test 15", "Surname Test 15", "PostalAddress Test 15",
						"numero de telefono", "alvarogoles@gmail.com", this.getDate(1997, 10, 30),
						"usernameTest15", "passwordTest15", org.springframework.dao.DataIntegrityViolationException.class},
				// User::email email
				{"Name Test 16", "Surname Test 16", "PostalAddress Test 16",
							"+34 955060405", "correo electronico", this.getDate(1997, 10, 30),
							"usernameTest16", "passwordTest16", org.springframework.dao.DataIntegrityViolationException.class},
				// User::birthdate notNull
				{"Name Test 17", "Surname Test 17", "PostalAddress Test 17",
							"+34 955060405", "alvarogoles@gmail.com", null,
							"usernameTest17", "passwordTest17", org.springframework.dao.DataIntegrityViolationException.class},
				// User::birthdate past
				{"Name Test 18", "Surname Test 18", "PostalAddress Test 18",
							"+34 955060405", "alvarogoles@gmail.com", this.getDate(2019, 10, 30),
							"usernameTest18", "passwordTest18", org.springframework.dao.DataIntegrityViolationException.class},
				// User::userAccount::password size=4
				{"Name Test 19", "Surname Test 19", "PostalAddress Test 19",
							"+34 955060405", "alvarorex@gmail.com", this.getDate(1997, 10, 30),
							"usernameTest19", "pass", org.springframework.dao.DataIntegrityViolationException.class},
				// User::userAccount::password size=33
				{"Name Test 20", "Surname Test 20", "PostalAddress Test 20",
							"+34 955060405", "alvarorex@gmail.com", this.getDate(1997, 10, 30), 
							"usernameTest20", "passwordTest17161415090807YYtT321", org.springframework.dao.DataIntegrityViolationException.class},
				// User::userAccount::username unique username
				{"Name Test 21", "Surname Test 21", "PostalAddress Test 21",
							"+34 955060405", "alvarorex@gmail.com", this.getDate(1997, 10, 30), "user1", "passwordTest21",
							org.springframework.dao.DataIntegrityViolationException.class}
		};
		
		for(int i=0;i<testingData.length;i++)  {
			template((String)testingData[i][0],
					 (String)testingData[i][1],
					 (String)testingData[i][2],
					 (String)testingData[i][3],
					 (String)testingData[i][4],
					 (Date)testingData[i][5],
					 (String)testingData[i][6],
					 (String)testingData[i][7],
					 (Class<?>)testingData[i][8]);
		}
		
	}
	
	protected void template(String name,String surname, String postalAddress,
			String phoneNumber, String email, Date birthdate, String username, String password, Class<?> expected) {
		Class<?> caught;
		caught = null;
		User user;
		UserAccount userAccount;
		
		try {
			super.startTransaction();
			super.authenticate(null);
			
			user = this.userService.create();
			
			userAccount = user.getUserAccount();
			userAccount.setUsername(username);
			userAccount.setPassword(password);
			
			user.setName(name);
			user.setSurname(surname);
			user.setPostalAddress(postalAddress);
			user.setPhoneNumber(phoneNumber);
			user.setEmail(email);
			user.setBirthdate(birthdate);
			user.setUserAccount(userAccount);
			
			this.userService.save(user);
			this.userService.flush();
			
			super.authenticate(null);
			super.unauthenticate();
			super.rollbackTransaction();
		} catch (Throwable oops) {
			caught = oops.getClass();
			
			super.rollbackTransaction();
		}
		
		checkExceptions(expected, caught);
	}
	
	private Date getDate(int year, int month, int date) {
		Calendar c;
		Date res;
		
		c = Calendar.getInstance();
		c.set(year, month, date);
		res = c.getTime();
		
		return res;
	}
	
}
