package et.com.gebeya.inventory_management.service.imp;

import et.com.gebeya.inventory_management.Models.Category;
import et.com.gebeya.inventory_management.Models.Product;
import et.com.gebeya.inventory_management.dto.CreateProductRequest;
import et.com.gebeya.inventory_management.dto.ProductDTO;
import et.com.gebeya.inventory_management.repos.CategoryRepository;
import et.com.gebeya.inventory_management.repos.ProductRepository;
import et.com.gebeya.inventory_management.service.listOfMethods.ProductService;
import et.com.gebeya.inventory_management.utility.MappingFunctions;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImplementation implements ProductService {
        private ProductRepository productRepository;
        private CategoryRepository categoryRepository;
        private MappingFunctions mappingFunctions;

        @Override
        public List<ProductDTO> listAllProducts() {
            return productRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        private ProductDTO convertToDTO(Product product) {
            ProductDTO dto = new ProductDTO();
            //dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setDiscount(product.getDiscount());
            dto.setProductDetail(product.getProductDetail());
            dto.setQuantity(product.getQuantity());
            dto.setImageUrl(product.getImageUrl());
            dto.setCategory(product.getCategory());
            return dto;
        }

        @Override
        public ProductDTO getProductById(Long id) {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            return mappingFunctions.convertToDTOForProduct(product);
        }

    @Override
    public ProductDTO savedProduct(ProductDTO productDTO) {
            Product product = mappingFunctions.convertToEntityForProduct(productDTO);
            Category category = categoryRepository.findById(productDTO.getCategory().getId())
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(String.valueOf(productDTO.getCategory().getName()));
                        newCategory.setId(productDTO.getCategory().getId());
                        newCategory.setDescription(String.valueOf(productDTO.getCategory().getDescription()));
                        newCategory.setImageUrl(String.valueOf(productDTO.getCategory().getImageUrl()));
                        return categoryRepository.save(newCategory);
                    });
            product.setCategory(category);
            Product savedProduct = productRepository.save(product);
            return mappingFunctions.convertToDTOForProduct(savedProduct);
        }
    public ProductDTO savedProductWithCategory(CreateProductRequest createProductRequest) {
        Product product = mappingFunctions.convertToEntityForProductWithCategory(createProductRequest);
        Optional<Category> category = categoryRepository.findById(createProductRequest.getCategory().getId());
                category.orElseThrow();
        product.setCategory(createProductRequest.getCategory());
        Product savedProduct = productRepository.save(product);
        return mappingFunctions.convertToDTOForProduct(savedProduct);
    }

        @Override
        public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            mappingFunctions.updateEntityWithDTOForProduct(productDTO, existingProduct);
            Product updatedProduct = productRepository.save(existingProduct);
            return mappingFunctions.convertToDTOForProduct(updatedProduct);
        }

        @Override
        public void deleteProduct(Long id) {
            productRepository.deleteById(id);
        }

    @Override
    public List<Product> getProductsUnderVendor(Long id) {
        return null;
    }

    @Override
    public void updateStock(Long productId, int quantity) {

    }

}
