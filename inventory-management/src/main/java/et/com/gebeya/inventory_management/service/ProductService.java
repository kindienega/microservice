package et.com.gebeya.inventory_management.service;

import et.com.gebeya.inventory_management.Models.Category;
import et.com.gebeya.inventory_management.Models.Product;
import et.com.gebeya.inventory_management.dto.ProductDTO;
import et.com.gebeya.inventory_management.dto.request.ProductCreationRequest;
import et.com.gebeya.inventory_management.dto.request.ProductDto;
import et.com.gebeya.inventory_management.dto.request.RequestForUpdate;
import et.com.gebeya.inventory_management.dto.response.ListAllProductUnderCategoryResponse;
import et.com.gebeya.inventory_management.dto.response.ProductCreationResponse;
import et.com.gebeya.inventory_management.dto.response.QuantityResponse;
import et.com.gebeya.inventory_management.exceptions.ResourceNotFoundException;
import et.com.gebeya.inventory_management.repos.CategoryRepository;
import et.com.gebeya.inventory_management.repos.ProductRepository;
import et.com.gebeya.inventory_management.utility.CustomMappingFunctions;
import et.com.gebeya.inventory_management.utility.MappingFunctions;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private final CustomMappingFunctions customMappingFunctions;

    private MappingFunctions mappingFunctions;
    public ProductCreationResponse createProduct(ProductCreationRequest request, MultipartFile imageFile) {
        return customMappingFunctions.createProductAndConvertToResponse(request, imageFile);
    }
    public List<ProductDTO> listAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setDescription(product.getDescription());
        dto.setDiscount(product.getDiscount());
        dto.setImageUrl(product.getImageUrl());
        dto.setCalories(product.getCalories());
        dto.setFat(product.getFat());
        dto.setProtein(product.getProtein());
        dto.setWeight(product.getWeight());
        dto.setSize(product.getSize());
        dto.setVolume(product.getVolume());
        dto.setBrands(product.getBrands());
        dto.setCategory(product.getCategory());
        return dto;
    }
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id not  found " + id));
        return mappingFunctions.convertToDTOForProduct(product);
    }
    public ProductDTO updateProduct(Long id, RequestForUpdate update) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with  "+id));
        mappingFunctions.updateEntityWithDTOForProduct(update, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        return mappingFunctions.convertToDTOForProduct(updatedProduct);
    }
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    public List<Product> getProductsUnderVendor(Long id) {
        return null;
    }
    public void updateStock(Long productId, int quantity) {

    }
    public List<ProductDTO> searchByName(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return products.stream()
                .map(mappingFunctions::convertToDTOForProduct)
                .collect(Collectors.toList());
    }
    public ListAllProductUnderCategoryResponse listAllProductsUnderCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
        List<Product> products = productRepository.findAllByCategory(category);
        List<ProductDto> productDtos = products.stream()
                .map(product -> new ProductDto(product.getId(),
                        product.getName(),
                        product.getQuantity(),
                        product.getPrice(),
                        product.getDescription(),
                        product.getImageUrl(),
                        product.getDiscount(),
                        product.getCategory(),
                        product.getCalories(),
                        product.getFat(),
                        product.getProtein(),
                        product.getWeight(),
                        product.getSize(),
                        product.getVolume(),
                        product.getBrands()
                        ))
                .collect(Collectors.toList());
        ListAllProductUnderCategoryResponse response = new ListAllProductUnderCategoryResponse();
        response.setProducts(productDtos);

        return response;
    }

//    public int getTotalQuantity(Long productId) {
//        return productRepository.findById(productId)
//                .map(Product::getQuantity)
//                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
//    }
public ResponseEntity<QuantityResponse> getTotalQuantity(Long productId) {
    int quantity = productRepository.findById(productId)
            .map(Product::getQuantity)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

    QuantityResponse response = new QuantityResponse(quantity);
    return ResponseEntity.ok(response);
}
    @Transactional
    public Product restockProduct(Long productId, int quantityToAdd) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
        product.setQuantity(product.getQuantity() + quantityToAdd);
        return productRepository.save(product);
    }
    @Transactional
    public Product decreaseStock(Long productId, int quantityToDecrease) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
        int newQuantity = product.getQuantity() - quantityToDecrease;

        if (newQuantity < 0) {
            throw new IllegalArgumentException("Insufficient stock for product ID: " + productId);
        }

        product.setQuantity(newQuantity);
        return productRepository.save(product);
    }



}
