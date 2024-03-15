package et.com.gebeya.inventory_management.repos;

import et.com.gebeya.inventory_management.Models.Category;
import et.com.gebeya.inventory_management.Models.Product;
import et.com.gebeya.inventory_management.dto.request.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory(Category category);

    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByIdIn(List<Long> ids);


    // List<Product> findAllByAdmin(Admins admins);


}
