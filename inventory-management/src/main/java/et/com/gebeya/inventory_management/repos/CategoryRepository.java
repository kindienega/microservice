package et.com.gebeya.inventory_management.repos;

import et.com.gebeya.inventory_management.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

}
