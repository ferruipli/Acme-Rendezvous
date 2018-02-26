package services;

import java.util.Calendar;
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

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RendezvousServiceTest extends AbstractTest {

	// Services under testing ----------------------------------------------
	@Autowired
	private RendezvousService rendezvousService;
	
	// Supporting services
	@Autowired
	private ActorService actorService;
	
	@Test
	public void testFindOne() {
		super.authenticate("user1");
		
		int rendezvousId;
		Rendezvous rendezvous;
		
		rendezvousId = 332;
		rendezvous = this.rendezvousService.findOne(rendezvousId);
		
		Assert.notNull(rendezvous);
		
		super.authenticate(null);
	}
	
	@Test
	public void testCreate() {
		super.authenticate("user1");
		
		User user;
		Rendezvous rendezvous;
		
		user = (User)this.actorService.findByPrincipal();
		rendezvous = this.rendezvousService.create();
		
		Assert.notNull(rendezvous);
		Assert.notNull(rendezvous.getAnnouncements());
		Assert.notNull(rendezvous.getAttendants());
		Assert.notNull(rendezvous.getComments());
		Assert.notNull(rendezvous.getCreator());
		Assert.isTrue(rendezvous.getCreator().getUserAccount().getUsername().equals(user.getUserAccount().getUsername()));
		Assert.notNull(rendezvous.getQuestions());
		Assert.notNull(rendezvous.getReserves());
		Assert.notNull(rendezvous.getSimilarOnes());
		
		Assert.isNull(rendezvous.getDescription());
		Assert.isNull(rendezvous.getGpsCoordinates());
		Assert.isNull(rendezvous.getMoment());
		Assert.isNull(rendezvous.getName());
		
		super.authenticate(null);
	}
	
	@Test
	public void testSave() {
		super.authenticate("user1");
		
		User user;
		Rendezvous rendezvous, saved;
		Calendar c;
		Date moment;
		
		user = (User)this.actorService.findByPrincipal();
		rendezvous = this.rendezvousService.create();
		
		c = Calendar.getInstance();
		c.set(2018, 04, 25, 20, 00);
		moment = c.getTime();
	
		rendezvous.setName("Name X");
		rendezvous.setDescription("Description X");
		rendezvous.setMoment(moment);
		
		saved = this.rendezvousService.save(rendezvous);
		
		Assert.isTrue(this.rendezvousService.findAll().contains(saved));
		Assert.isTrue(saved.getId()!=0);
		Assert.isTrue(user.getCreatedRendezvouses().contains(saved));
		
		super.authenticate(null);
	}
	
	@Test
	public void testDelete() {
		super.authenticate("user1");
		
		Rendezvous rendezvous, saved;
		Date moment;
		Calendar c;
		
		c = Calendar.getInstance();
		c.set(2018, 04, 25, 20, 00);
		moment = c.getTime();
		
		rendezvous = this.rendezvousService.create();
		
		rendezvous.setName("Name X");
		rendezvous.setDescription("Description X");
		rendezvous.setMoment(moment);
		
		saved = this.rendezvousService.save(rendezvous);
		
		Assert.isTrue(saved.getId() != 0);
		
		this.rendezvousService.delete(saved);
		
		Assert.isTrue(this.rendezvousService.findAll().contains(saved));
		Assert.isTrue(this.rendezvousService.findAllAvailable().contains(saved));
		Assert.isTrue(saved.getFinalMode());
		Assert.isTrue(saved.getIsFlagged());
		
		super.authenticate(null);
	}
	
	@Test
	public void testRemove() {
		super.authenticate("user2");
		
		Rendezvous rendezvous1, rendezvous2;
		User user;
		
		rendezvous1 = this.getRendezvous(333);
		rendezvous2 = this.getRendezvous(332);
		user = (User)this.actorService.findByPrincipal();
		
		Assert.isTrue(rendezvous1.getSimilarOnes().contains(rendezvous2));
		
		this.rendezvousService.remove(rendezvous2);
		
		Assert.isTrue(!user.getCreatedRendezvouses().contains(rendezvous2));
		Assert.isTrue(!this.rendezvousService.findAll().contains(rendezvous2));
		Assert.isTrue(!rendezvous1.getSimilarOnes().contains(rendezvous2));
		
		super.authenticate(null);
	}
	
	private Rendezvous getRendezvous(int id) {
		Rendezvous result;
	
		result = this.rendezvousService.findOne(id);
		
		return result;
	}
	
}
