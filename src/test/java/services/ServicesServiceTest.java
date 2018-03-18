
package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Manager;
import domain.Services;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ServicesServiceTest extends AbstractTest {

	// Service under testing -----------------------------------

	@Autowired
	private ServicesService	servicesService;

	// Other services ------------------------------------------

	@Autowired
	private ActorService	actorService;


	// Test ----------------------------------------------------

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

	// Driver and template -------------------------------------

	@Test
	public void testDriverServiceManagement() {
		final Object testingData[][] = {
			// MANAGER crea, guarda, lista y borra servicio
			{
				"manager1", "Testing name1", "Testing description1", "http://testing.url.com", null, false, null
			},
			// ADMINISTRATOR crea, guarda, lista y borra servicio
			{
				"admin", "Testing name2", "Testing description2", "http://testing.url.com", null, false, IllegalArgumentException.class
			},
			// crear y guardar servicio con nombre inválido
			{
				"manager1", "", "Testing description3", "http://testing.url.com", null, false, ConstraintViolationException.class
			},
			// crear y guardar servicio con descripción inválida
			{
				"manager1", "Testing name4", "", "http://testing.url.com", null, false, ConstraintViolationException.class
			},
			// crear y guardar servicio con urlPicture inválida
			{
				"manager1", "Testing name5", "Testing description5", "testing.url", null, false, ConstraintViolationException.class
			},
			// MANAGER actualiza y borra servicio
			{
				"manager1", "Testing name6", "Testing description6", "http://testing.url.com", "service3", false, null
			},
			// MANAGER actualiza servicio que no es suyo
			{
				"manager1", "Testing name7", "Testing description7", "http://testing.url.com", "service5", false, IllegalArgumentException.class
			},
			// MANAGER actualiza servicio suyo que ya ha sido solicitado
			{
				"manager1", "Testing name8", "Testing description8", "http://testing.url.com", "service1", false, IllegalArgumentException.class
			},
			// MANAGER borra servicio que no es suyo
			{
				"manager1", "Testing name9", "Testing description9", "http://testing.url.com", "service5", true, IllegalArgumentException.class
			},
			// MANAGER borra servicio suyo que ya ha sido solicitado
			{
				"manager1", "Testing name10", "Testing description10", "http://testing.url.com", "service1", true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateServiceManagement((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (boolean) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	/* El método save y delete tienen restricciones similares. Para probar las restricciones de ambos métodos de forma aislada, se ha añadido el atributo onlyDelete, para evitar que se haga en save en el test */
	protected void templateServiceManagement(final String username, final String name, final String description, final String urlPicture, final String serviceBeanName, final boolean onlyDelete, final Class<?> expected) {
		Class<?> caught;
		Services service, aux, saved;
		int serviceId;
		Manager principal;

		caught = null;
		try {
			super.startTransaction();
			super.authenticate(username);

			service = this.servicesService.create();

			if (null != serviceBeanName) {
				/*
				 * Creamos un objeto Service copia, para que al hacer los setters
				 * para actualizar no se hagan los cambios directamente en la base
				 * de datos sin llamar al método save.
				 */
				serviceId = super.getEntityId(serviceBeanName);
				aux = this.servicesService.findOne(serviceId);
				this.copyService(service, aux);
			}

			service.setName(name);
			service.setDescription(description);
			service.setUrlPicture(urlPicture);
			if (!onlyDelete) {
				saved = this.servicesService.save(service);
				this.servicesService.flush();
			} else
				saved = service;

			principal = (Manager) this.actorService.findByPrincipal();
			Assert.isTrue(principal.getServices().contains(saved), "Manager principal does not have the created/updated service.");
			this.servicesService.delete(saved);
			this.servicesService.flush();
			Assert.isTrue(!principal.getServices().contains(saved), "Manager principal still has the deleted service");

			super.unauthenticate();
			super.rollbackTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			super.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	protected void copyService(final Services target, final Services source) {
		target.setId(source.getId());
		target.setDescription(source.getDescription());
		target.setIsCancelled(source.getIsCancelled());
		target.setIsRequested(source.getIsRequested());
		target.setName(source.getName());
		target.setUrlPicture(source.getUrlPicture());
		target.setVersion(source.getVersion());
	}

}
