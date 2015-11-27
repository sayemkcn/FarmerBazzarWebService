package csecarnival.uapadventurers.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import csecarnival.uapadventurers.dto.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	Product findById(Long id);
	@Query(value="SELECT DISTINCT categoryName FROM Product")
    List<String> findDistinctCategoryName();
	List<Product> findBySubCategoryName(String subCategoryName);
}
