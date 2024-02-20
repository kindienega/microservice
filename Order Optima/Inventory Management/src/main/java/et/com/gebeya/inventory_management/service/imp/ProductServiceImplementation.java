package et.com.gebeya.inventory_management.service.imp;

import et.com.gebeya.inventory_management.Models.Category;
import et.com.gebeya.inventory_management.Models.Product;
import et.com.gebeya.inventory_management.dto.ProductDTO;
import et.com.gebeya.inventory_management.repos.CategoryRepository;
import et.com.gebeya.inventory_management.repos.ProductRepository;
import et.com.gebeya.inventory_management.service.listOfMethods.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImplementation implements ProductService {
        private ProductRepository productRepository;
        private CategoryRepository categoryRepository;

        @Override
        public List<ProductDTO> listAllProducts() {
            return productRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        @Override
        public ProductDTO getProductById(Long id) {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            return convertToDTO(product);
        }

    @Override
    public ProductDTO savedProduct(ProductDTO productDTO) {
            Product product = convertToEntity(productDTO);
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
            return convertToDTO(savedProduct);
        }

        @Override
        public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            updateEntityWithDTO(productDTO, existingProduct);
            Product updatedProduct = productRepository.save(existingProduct);
            return convertToDTO(updatedProduct);
        }

        @Override
        public void deleteProduct(Long id) {
            productRepository.deleteById(id);
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

        private Product convertToEntity(ProductDTO dto) {
            Product product = new Product();
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setDiscount(dto.getDiscount());
            product.setProductDetail(dto.getProductDetail());
            product.setQuantity(dto.getQuantity());
            product.setCategory(dto.getCategory());
            product.setImageUrl(dto.getImageUrl());
            return product;
        }

        private void updateEntityWithDTO(ProductDTO dto, Product product) {
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setProductDetail(dto.getProductDetail());
            product.setDiscount(dto.getDiscount());
            product.setImageUrl(dto.getImageUrl());
            product.setQuantity(dto.getQuantity());
            product.setCategory(dto.getCategory());
        }
}
