package et.com.gebeya.inventory_management.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import et.com.gebeya.inventory_management.customService.ProductServices;
//import et.com.gebeya.inventory_management.dto.request.StockAdjustmentDTO;
//import et.com.gebeya.inventory_management.dto.response.ListAllProductUnderCategoryResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/product")
//public class CustomController {
//    @Autowired
//    private ProductServices productServices;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @PostMapping
//    public ResponseEntity<ProductCreationResponse> createProduct(@RequestBody ProductCreationRequest request, MultipartFile imageFile) {
//        ProductCreationResponse response = productServices.createProduct(request, imageFile);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/category/{categoryId}")
//    public ResponseEntity<ListAllProductUnderCategoryResponse> listAllProductsUnderCategory(@PathVariable Long categoryId) {
//        ListAllProductUnderCategoryResponse response = productServices.listAllProductsUnderCategory(categoryId);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
////    @GetMapping("/admin/{adminId}")
////    public ResponseEntity<ListOfAllProductUnderAdminResponse> listAllProductsUnderAdmin(@PathVariable Long adminId) {
////        ListOfAllProductUnderAdminResponse response = productServices.listAllProductsUnderAdmin(adminId);
////        return new ResponseEntity<>(response, HttpStatus.OK);
////    }
//    @GetMapping("/{productId}/stock")
//    public ResponseEntity<Integer> getTotalStock(@PathVariable Long productId) {
//        int totalQuantity = productServices.getTotalQuantity(productId);
//        return ResponseEntity.ok(totalQuantity);
//    }
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/restock")
//    public ResponseEntity<Void> restockProduct(@RequestBody StockAdjustmentDTO stockAdjustment) {
//        productServices.restockProduct(stockAdjustment.getProductId(), stockAdjustment.getQuantity());
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/order")
//    public ResponseEntity<Void> orderProduct(@RequestBody StockAdjustmentDTO stockAdjustment) {
//        productServices.decreaseStock(stockAdjustment.getProductId(), stockAdjustment.getQuantity());
//        return ResponseEntity.ok().build();
//    }
//}
