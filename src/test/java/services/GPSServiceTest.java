package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.GPS;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class GPSServiceTest extends AbstractTest {
/*
	// Service under testing
	@Autowired
	private GPSService gpsService;
	
	@Test
	public void testCreate() {
		super.authenticate("user1");
		
		GPS gps;
		
		gps = this.gpsService.create();
		
		Assert.notNull(gps);
		Assert.isTrue(gps.getLatitude() == 0.0);
		Assert.isTrue(gps.getLongitude() == 0.0);
		
		super.authenticate(null);
	}
	
	@Test
	public void testSave() {
		super.authenticate("user1");
		
		GPS gps, saved;
		
		gps = this.gpsService.create();
		gps.setLatitude(40.4167754);
		gps.setLongitude(-3.7037901999999576);
		
		saved = this.gpsService.save(gps);
		
		Assert.isTrue(this.gpsService.findAll().contains(saved));
		
		super.authenticate(null);
	}
	
	@Test
	public void deleteTest() {
		super.authenticate("user1");
		
		GPS gps, saved;
		
		gps = this.gpsService.create();
		gps.setLatitude(40.4167754);
		gps.setLongitude(-3.7037901999999576);
		
		saved = this.gpsService.save(gps);
		
		Assert.isTrue(this.gpsService.findAll().contains(saved));
		
		this.gpsService.delete(saved);
		
		Assert.isTrue(!this.gpsService.findAll().contains(saved));
		
		super.authenticate(null);
	}
	*/
}
