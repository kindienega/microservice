package et.com.gebeya.inventory_management.utility;

import et.com.gebeya.inventory_management.Models.Category;
import et.com.gebeya.inventory_management.Models.Product;
import et.com.gebeya.inventory_management.cloudinary.ImageModel;
import et.com.gebeya.inventory_management.cloudinary.ImageServiceImpl;
import et.com.gebeya.inventory_management.dto.CreateProductRequest;
import et.com.gebeya.inventory_management.dto.ProductDTO;
import et.com.gebeya.inventory_management.dto.request.CategoryRegistrationRequest;
import et.com.gebeya.inventory_management.dto.request.RequestForUpdate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Component
@AllArgsConstructor
public class MappingFunctions {

    public CategoryRegistrationRequest convertToDTOForCategory(Category category) {
        CategoryRegistrationRequest request = new CategoryRegistrationRequest();
        request.setName(category.getName());
        request.setTittle(category.getTittle());
        request.setMetaTittle(category.getMetaTittle());
        request.setDescription(category.getDescription());
        return request;
    }
    public Category convertToEntityForCategory(CategoryRegistrationRequest request){
        Category category = new Category();
        category.setName(request.getName());
        category.setTittle(request.getTittle());
        category.setMetaTittle(request.getMetaTittle());
        category.setDescription(request.getDescription());
        return category;
    }
    public void updateEntityWithDtoForCategory(CategoryRegistrationRequest dto, Category category) {
        category.setName(dto.getName());
        category.setTittle(dto.getTittle());
        category.setMetaTittle(dto.getMetaTittle());
        category.setDescription(dto.getDescription());
    }


    public ProductDTO convertToDTOForProduct(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setDiscount(product.getDiscount());
        dto.setQuantity(product.getQuantity());
        dto.setCalories(product.getCalories());
        dto.setFat(product.getFat());
        dto.setProtein(product.getProtein());
        dto.setWeight(product.getWeight());
        dto.setSize(product.getSize());
        dto.setVolume(product.getVolume());
        dto.setBrands(product.getBrands());
        dto.setImageUrl(product.getImageUrl());
        dto.setCategory(product.getCategory());
        return dto;
    }

    public Product convertToEntityForProduct(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setDiscount(dto.getDiscount());
        product.setQuantity(dto.getQuantity());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        return product;
    }
    public Product convertToEntityForProductWithCategory(CreateProductRequest dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setDiscount(dto.getDiscount());
        product.setQuantity(dto.getQuantity());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        return product;
    }

    public void updateEntityWithDTOForProduct(RequestForUpdate dto, Product product) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setDiscount(dto.getDiscount());
        product.setQuantity(dto.getQuantity());
        //product.setCategory(dto.getCategory());
        product.setCalories(dto.getCalories());
        product.setFat(dto.getFat());
        product.setProtein(dto.getProtein());
        product.setWeight(dto.getCalories());
        product.setSize(dto.getSize());
        product.setVolume(dto.getVolume());
        product.setBrands(dto.getBrands());
        product.setImageUrl(dto.getImageUrl());
    }

}
