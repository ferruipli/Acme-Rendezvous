package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Comment;
import domain.RSVP;
import domain.Rendezvous;
import domain.User;

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
	
	// Test -------------------------------------------------
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
	
	@Test
	public void driver() {
		Object testingData[][] = {
				{"Name Test 1", "Surname Test 1", "PostalAddress Test 1",
					"+34 955060405", "alvarorex@gmail.com", this.getDate(1997, 10, 30),
					Collections.<Comment>emptySet(), Collections.<RSVP>emptySet(),
					Collections.<Rendezvous>emptySet(), null},
				{"", "Surname Test 2", "PostalAddress Test 2",
					"+34 955060405", "alvarorex@gmail.com", this.getDate(1997, 10, 30),
					Collections.<Comment>emptySet(), Collections.<RSVP>emptySet(),
					Collections.<Rendezvous>emptySet(), ConstraintViolationException.class},
				{"Name Test 3", "Surname Test 3", "PostalAddress Test 3",
						"+34 955060405", "alvarorex@gmail.com", this.getDate(1997, 10, 30),
						Collections.<Comment>emptySet(), Collections.<RSVP>emptySet(),
						Collections.<Rendezvous>emptySet(), ConstraintViolationException.class},
		};
	}
	
	protected void template(String name,String surname, String postalAddress,
			String phoneNumber, String email, Date birthdate,
			Collection<Comment> comments, Collection<RSVP> reserves,
			Collection<Rendezvous> createdRendezvouses, Class<?> expected) {
		Class<?> caught;
		caught = null;
		User user;
		
		try {
			user = this.userService.create();
			user.setName(name);
			user.setSurname(surname);
			user.setPostalAddress(postalAddress);
			user.setPhoneNumber(phoneNumber);
			user.setEmail(email);
			user.setBirthdate(birthdate);
			user.setComments(comments);
			user.setReserves(reserves);
			user.setCreatedRendezvouses(createdRendezvouses);
		} catch (Throwable oops) {
			caught = oops.getClass();
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
