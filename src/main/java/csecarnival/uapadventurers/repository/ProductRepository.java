package csecarnival.uapadventurers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import csecarnival.uapadventurers.dto.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
