package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

}
