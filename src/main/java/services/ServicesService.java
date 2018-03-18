
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ServicesRepository;
import domain.Manager;
import domain.Request;
import domain.Services;

@Service
@Transactional
public class ServicesService {

	// Managed repository ----------------------------

	@Autowired
	private ServicesRepository	serviceRepository;

	// Supporting services ---------------------------

	@Autowired
	private RequestService		requestService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private Validator			validator;


	// Constructors ----------------------------------
	public ServicesService() {
		super();
	}

	// CRUD Services ---------------------------------

	public Services create() {
		Services result;

		result = new Services();

		return result;
	}

	public Services save(final Services service) {
		Services result;
		Manager principal;
		int serviceId;

		serviceId = service.getId();
		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		if (serviceId != 0) {
			Assert.isTrue(principal.getServices().contains(service));
			Assert.isTrue(!service.getIsRequested());
		}

		result = this.serviceRepository.save(service);
		this.managerService.addService(principal, result);

		return result;
	}

	public void delete(final Services service) {
		Assert.isTrue(service.getId() != 0);

		Manager principal;

		principal = this.managerService.findByPrincipal();

		Assert.isTrue(!service.getIsRequested());
		Assert.isTrue(principal.getServices().contains(service));

		this.managerService.removeService(principal, service);
		this.serviceRepository.delete(service);
	}

	public Services findOne(final int serviceId) {
		Services result;

		result = this.serviceRepository.findOne(serviceId);

		return result;
	}

	public Collection<Services> findAll() {
		Collection<Services> result;

		result = this.serviceRepository.findAll();

		return result;
	}

	// Other business methods ------------------------

	public Services reconstruct(final Services service, final BindingResult binding) {
		Services result, stored;

		if (service.getId() == 0)
			result = service;
		else {
			stored = this.findOne(service.getId());
			result = this.create();

			result.setId(stored.getId());
			result.setVersion(stored.getVersion());
			result.setIsCancelled(stored.getIsCancelled());
			result.setIsRequested(stored.getIsRequested());

			result.setName(service.getName());
			result.setDescription(service.getDescription());
			result.setUrlPicture(service.getUrlPicture());

			this.validator.validate(result, binding);
		}

		return result;
	}

	public void cancel(final Services services) {
		Assert.notNull(services);
		Assert.isTrue(services.getId() != 0 && services.getIsCancelled() == false);

		Collection<Request> requests;

		// Update requests about this services
		requests = this.requestService.findAll();

		for (final Request r : requests)
			if (r.getService().equals(services))
				this.requestService.delete(r);

		services.setIsCancelled(true);
	}

	public Collection<Services> availableServices() {
		Collection<Services> result;

		result = this.serviceRepository.getAvailableServices();

		return result;
	}

	public Collection<Services> cancelledServices() {
		Collection<Services> result;

		result = this.serviceRepository.getCancelledServices();

		return result;
	}
	public Services findBestSellingService() {
		Services result;

		result = this.serviceRepository.findBestSellingService();

		return result;
	}

	public void flush() {
		this.serviceRepository.flush();
	}

}
