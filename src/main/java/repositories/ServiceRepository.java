package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Service;

public interface ServiceRepository extends JpaRepository<Service,Integer>{

}
