
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.Request;

@Service
@Transactional
public class RequestService {

	// Managed repository
	@Autowired
	private RequestRepository	requestRepository;


	// Supporting services ------------------------


	// Constructors -------------------------------
	public RequestService() {
		super();
	}

	// CRUD methods -------------------------------
	
	public Request create() {
		Request result;
		
		result = new Request();

		return result;
	}
	
	public Request save(final Request request) {
		Assert.notNull(request);
		Assert.notNull(request.getRendezvous());
		Assert.notNull(request.getService());
		Assert.notNull(request.getCreditCard());
		Assert.isTrue(!this.requestRepository.exists(request.getId()));

		Request result;
	
		result = this.requestRepository.save(request);
		
		if(!request.getService().getIsRequested())
			request.getService().setIsRequested(true);

		return result;
	}
	
	public Collection<Request> findAll() {
		Collection<Request> requests;

		requests = this.requestRepository.findAll();

		return requests;
	}

	public void delete(final Request request) {
		Assert.notNull(request);
		Assert.isTrue(request.getId() != 0);

		this.requestRepository.delete(request);
	}
	
	// Other business methods ---------------------
	
}
