package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor,Integer> {
	
	@Query("select u from User u where u.userAccount.id=?1")
	Actor getUserByUserAccount(int userAccountId);

}