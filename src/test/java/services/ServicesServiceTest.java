package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Services;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/junit.xml"})
@Transactional
public class ServicesServiceTest extends AbstractTest {

	// Service under testing -----------------------------------
	@Autowired
	private ServicesService servicesService;
	
	// Other services ------------------------------------------
	
	// Test ------------------------------------------------
	
	@Test
	public void testCancelService() {
		int serviceId;
		Services service;
		serviceId = super.getEntityId("service1");
		
		super.authenticate("admin");
		
		service = this.servicesService.findOne(serviceId);
		
		this.servicesService.cancel(service);
		
		Assert.isTrue(service.getIsCancelled());
		
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCancelNullService() {
		int serviceId;
		Services service;
		serviceId = 1;
		
		super.authenticate("admin");
		/* No existe ningún con servicio con el id igual a 1. Por tanto, se le asignará el valor null al objeto */
		service = this.servicesService.findOne(serviceId);
		
		this.servicesService.cancel(service);
		
		Assert.isTrue(service.getIsCancelled());
		
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCancelNotSavedService() {
		Services service;
				
		service = this.servicesService.create();
		service.setName("Service X");
		service.setDescription("Description X");
		
		super.authenticate("admin");
		
		this.servicesService.cancel(service);
		
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCancelCancelledService() {
		int serviceId;
		Services service;
		serviceId = super.getEntityId("service3");
		
		super.authenticate("admin");
		
		service = this.servicesService.findOne(serviceId);
		
		this.servicesService.cancel(service);
		
		Assert.isTrue(service.getIsCancelled());
		
		super.authenticate(null);
	}
	
}
