
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Services;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Integer> {

	@Query("select s from Services s where s.isCancelled = FALSE")
	Collection<Services> getAvailableServices();

	@Query("select s from Services s where s.isCancelled = TRUE")
	Collection<Services> getCancelledServices();

	@Query("select r1.service from Request r1 group by r1.service having count(r1) >= all(select count(r2) from Request r2 group by r2.service)")
	Services findBestSellingService();

}
