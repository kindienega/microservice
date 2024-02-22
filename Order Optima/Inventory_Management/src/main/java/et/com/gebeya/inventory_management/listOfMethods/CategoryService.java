package et.com.gebeya.inventory_management.service.listOfMethods;

import et.com.gebeya.inventory_management.dto.CategoryDTO;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface CategoryService {
    List<CategoryDTO> listAllCategory();
    CategoryDTO getCategoryById(Long id);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    void deleteCategory(Long id);
}