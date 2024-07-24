package et.com.gebeya.user_service.repository;

import et.com.gebeya.user_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    Optional<Product> findProductByName(String name);

}
