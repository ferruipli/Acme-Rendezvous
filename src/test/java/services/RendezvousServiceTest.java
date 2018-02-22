package services;

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
		Date moment;
		
		user = (User)this.actorService.findByPrincipal();
		rendezvous = this.rendezvousService.create();
		
		moment = new Date();
		
		rendezvous.setName("Name X");
		rendezvous.setDescription("Description X");
		rendezvous.setMoment(new Date(moment.getTime()+1000));
		
		saved = this.rendezvousService.save(rendezvous);
		
		Assert.isTrue(this.rendezvousService.findAll().contains(saved));
		Assert.isTrue(user.getCreatedRendezvouses().contains(rendezvous));
		
		super.authenticate(null);
	}
	
	@Test
	public void testDelete() {
		super.authenticate("user1");
		
		Rendezvous rendezvous, saved;
		Date moment;
		
		rendezvous = this.rendezvousService.create();
		
		moment = new Date();
		
		rendezvous.setName("Name X");
		rendezvous.setDescription("Description X");
		rendezvous.setMoment(new Date(moment.getTime()+1000));
		
		saved = this.rendezvousService.save(rendezvous);
		
		Assert.isTrue(this.rendezvousService.findAll().contains(saved));
		
		this.rendezvousService.delete(saved);
		
		Assert.isTrue(this.rendezvousService.findAll().contains(saved));
		Assert.isTrue(saved.getFinalMode());
		Assert.isTrue(saved.getIsFlagged());
		
		super.authenticate(null);
	}
	
}
