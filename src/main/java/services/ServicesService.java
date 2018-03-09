package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ServicesRepository;
import domain.Request;
import domain.Services;

@Service
@Transactional
public class ServicesService {

	// Managed repository ----------------------------
	@Autowired
	private ServicesRepository serviceRepository;
	
	// Supporting services ---------------------------
	@Autowired
	private RequestService requestService;
	
	// Constructors ----------------------------------
	public ServicesService() {
		super();
	}
	
	// CRUD Services ---------------------------------
	
	// Other business methods ------------------------
	
	public void cancel(Services services) {
		Assert.notNull(services);
		Assert.notNull(services.getId() != 0);
		
		Collection<Request> requests;
		
		// Update requests about this services
		requests = this.requestService.findAll();
		
		for (Request r: requests) {
			if (r.getService().equals(services)) {
				this.requestService.delete(r);
			}
		}
				
		services.setIsCancelled(true);
	}
	
}
