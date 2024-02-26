package et.com.gebeya.inventory_management.service.listOfMethods;

import et.com.gebeya.inventory_management.Models.Product;
import et.com.gebeya.inventory_management.dto.CreateProductRequest;
import et.com.gebeya.inventory_management.dto.ProductDTO;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ProductService {
    List<ProductDTO> listAllProducts();
    ProductDTO getProductById(Long id);
    ProductDTO savedProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
    List<Product> getProductsUnderVendor(Long id);
    void updateStock(Long productId, int quantity);
    public ProductDTO savedProductWithCategory(CreateProductRequest createProductRequest);
    }