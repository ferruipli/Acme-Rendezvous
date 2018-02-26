package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	// Service under testing ----------------------------------
	@Autowired
	private ActorService actorService;
	
	@Test
	public void edadTest() {
		super.authenticate("user1");
		
		Actor user;
		long edad;
		
		user = this.actorService.findByPrincipal(); 
		edad = this.actorService.getEdad(user);
		
		System.out.println("Edad:"+edad);
		
		Assert.isTrue(edad == 18);
		
		super.authenticate(null);
		super.authenticate("user2");
		
		user = this.actorService.findByPrincipal();
		edad = this.actorService.getEdad(user);
		
		System.out.println("Edad:"+edad);
		
		Assert.isTrue(edad == 20);
		
		super.authenticate(null);
		super.authenticate("user3");
		
		user = this.actorService.findByPrincipal();
		edad = this.actorService.getEdad(user);
		
		System.out.println("Edad:"+edad);
		
		Assert.isTrue(edad == 51);
		
		super.authenticate(null);
	}
	
}
