package et.com.gebeya.inventory_management.vendorProduct.servicess;

import et.com.gebeya.inventory_management.vendorProduct.Status;
import et.com.gebeya.inventory_management.vendorProduct.VendorProduct;
import et.com.gebeya.inventory_management.vendorProduct.request.ProductVendorCreationRequest;
import et.com.gebeya.inventory_management.vendorProduct.response.VendorProductCreationsResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/vendorProducts")
public class VendorProductController {
    private final VendorProductService vendorProductService;

    @PostMapping
    public ResponseEntity<VendorProductCreationsResponse> createVendorProduct(@RequestBody ProductVendorCreationRequest request) {
        VendorProductCreationsResponse response = vendorProductService.createVendorProduct(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{vendorProductId}/status/{status}")
    public ResponseEntity<VendorProduct> updateVendorProductStatus(@PathVariable Long vendorProductId, @PathVariable Status status) {
        VendorProduct updatedVendorProduct = vendorProductService.updateStatus(vendorProductId, status);
        return ResponseEntity.ok(updatedVendorProduct);
    }
}
