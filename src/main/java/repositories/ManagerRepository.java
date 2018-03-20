
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

	@Query("select m1 from Manager m1 join m1.services s1 where s1.isCancelled=1 group by m1.name having count(m1) >= all(select count(m2) from Manager m2 join m2.services s2 where s2.isCancelled=1 group by m2.name)")
	Collection<Manager> findManagerMoreServiceCancelled();

}
