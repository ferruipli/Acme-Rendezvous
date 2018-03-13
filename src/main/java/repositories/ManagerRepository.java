
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	@Query("select m from Manager m where m.userAccount.id = ?1")
	Manager findByUserAccountId(int userAccountId);

	@Query("select m.VAT from Manager m")
	Collection<String> findAllVATs();

	@Query("select m from Manager m where m.services.size > (select avg(ma.services.size)*1 from Manager ma));")
	Collection<Manager> findManagerMoreServiceThanAvg();

	//@Query("")
	//Collection<Manager> findManagerMoreServiceCancelled();

}
