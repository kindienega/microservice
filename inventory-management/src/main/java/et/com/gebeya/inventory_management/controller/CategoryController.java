package et.com.gebeya.inventory_management.controller;

import et.com.gebeya.inventory_management.dto.CategoryDTO;
import et.com.gebeya.inventory_management.dto.request.CategoryRegistrationRequest;
import et.com.gebeya.inventory_management.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/all")
    public List<CategoryDTO> getCategory(){
        return categoryService.listAllCategory();
    }
    @GetMapping("/{id}")
    public CategoryRegistrationRequest getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public CategoryRegistrationRequest addCategory(@RequestBody @Valid CategoryRegistrationRequest request){
         return categoryService.createCategory(request);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CategoryRegistrationRequest updateCategory(@PathVariable Long id, @RequestBody CategoryRegistrationRequest request){
        return categoryService.updateCategory(id, request);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
    }
}
