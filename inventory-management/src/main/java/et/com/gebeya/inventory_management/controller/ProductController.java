package et.com.gebeya.inventory_management.controller;

import et.com.gebeya.inventory_management.dto.CreateProductRequest;
import et.com.gebeya.inventory_management.dto.ProductDTO;
import et.com.gebeya.inventory_management.service.listOfMethods.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.listAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        try {
            return productService.getProductById(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping()
    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO){
        return productService.savedProduct(productDTO);
    }
    @PostMapping("/procate")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        ProductDTO savedProductDTO = productService.savedProductWithCategory(createProductRequest);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        try {
            return productService.updateProduct(id, productDTO);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}