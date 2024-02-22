package et.com.gebeya.inventory_management.repos;

import et.com.gebeya.inventory_management.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
