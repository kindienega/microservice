package et.com.gebeya.inventory_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import et.com.gebeya.inventory_management.Models.Product;
import et.com.gebeya.inventory_management.cloudinary.ImageModel;
import et.com.gebeya.inventory_management.cloudinary.ImageServiceImpl;
import et.com.gebeya.inventory_management.dto.ProductDTO;
import et.com.gebeya.inventory_management.dto.request.ProductCreationRequest;
import et.com.gebeya.inventory_management.dto.request.RequestForUpdate;
import et.com.gebeya.inventory_management.dto.request.StockAdjustmentDTO;
import et.com.gebeya.inventory_management.dto.response.ListAllProductUnderCategoryResponse;
import et.com.gebeya.inventory_management.dto.response.ProductCreationResponse;
import et.com.gebeya.inventory_management.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;
    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<ProductCreationResponse> createProduct(
            @RequestPart("product") String productJson,
            @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            ProductCreationRequest request = objectMapper.readValue(productJson, ProductCreationRequest.class);
            ProductCreationResponse response = productService.createProduct(request, imageFile);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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

//    @PostMapping()
//    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO){
//        return productService.savedProduct(productDTO);
//    }
//    @PostMapping("/productWithCategory")
//    public ResponseEntity<ProductDTO> createProduct(@RequestBody CreateProductRequest createProductRequest) {
//        ProductDTO savedProductDTO = productService.savedProductWithCategory(createProductRequest);
//        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
//    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody RequestForUpdate update) {
        try {
            return productService.updateProduct(id, update);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProductsByName(@RequestParam String name) {
        List<ProductDTO> products = productService.searchByName(name);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ListAllProductUnderCategoryResponse> listAllProductsUnderCategory(@PathVariable Long categoryId) {
        ListAllProductUnderCategoryResponse response = productService.listAllProductsUnderCategory(categoryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{productId}/stock")
    public ResponseEntity<Integer> getTotalStock(@PathVariable Long productId) {
        int totalQuantity = productService.getTotalQuantity(productId);
        return ResponseEntity.ok(totalQuantity);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/restock")
    public ResponseEntity<Void> restockProduct(@RequestBody StockAdjustmentDTO stockAdjustment) {
        productService.restockProduct(stockAdjustment.getProductId(), stockAdjustment.getQuantity());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/order")
    public ResponseEntity<Void> orderProduct(@RequestBody StockAdjustmentDTO stockAdjustment) {
        productService.decreaseStock(stockAdjustment.getProductId(), stockAdjustment.getQuantity());
        return ResponseEntity.ok().build();
    }
}