package et.com.gebeya.order_service.repository;


import et.com.gebeya.order_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
   // List<Product> findAllByCategory(Category category);

}
