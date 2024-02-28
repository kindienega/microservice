package et.com.gebeya.inventory_management.service;

import et.com.gebeya.inventory_management.Models.Category;
import et.com.gebeya.inventory_management.Models.Product;
import et.com.gebeya.inventory_management.dto.CategoryDTO;
import et.com.gebeya.inventory_management.repos.CategoryRepository;
import et.com.gebeya.inventory_management.utility.MappingFunctions;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private MappingFunctions mapper;

    public List<CategoryDTO> listAllCategory() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setImageUrl(category.getImageUrl());
        return dto;
    }
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapper.convertToDTOForCategory(category);
    }
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = mapper.convertToEntityForCategory(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return mapper.convertToDTOForCategory(savedCategory);
    }
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category ifExistCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(" category doest not exist"));
        mapper.updateEntityWithDtoForCategory(categoryDTO, ifExistCategory);
        Category updatedCategory = categoryRepository.save(ifExistCategory);
        return mapper.convertToDTOForCategory(updatedCategory);
    }
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
    public List<Product> getProductsUnderCategory(Long id) {
        return null;
    }
}
