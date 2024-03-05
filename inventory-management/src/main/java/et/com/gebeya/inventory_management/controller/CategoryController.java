package et.com.gebeya.inventory_management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import et.com.gebeya.inventory_management.dto.CategoryDTO;
import et.com.gebeya.inventory_management.dto.request.CategoryRegistrationRequest;
import et.com.gebeya.inventory_management.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
//    @PostMapping(value = "", consumes = "multipart/form-data")
//    public CategoryRegistrationRequest addCategory(@RequestBody CategoryRegistrationRequest request, MultipartFile imageFile){
//        return categoryService.createCategory(request, imageFile);
//    }
@PostMapping(value = "", consumes = "multipart/form-data")
public CategoryRegistrationRequest addCategory(@RequestParam("category") String categoryJson,
                                               @RequestParam("imageFile") MultipartFile imageFile)
        throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    CategoryRegistrationRequest request = objectMapper.readValue(categoryJson, CategoryRegistrationRequest.class);
    return categoryService.createCategory(request, imageFile);
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
