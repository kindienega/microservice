package et.com.gebeya.inventory_management.vendorProduct.request;

import et.com.gebeya.inventory_management.vendorProduct.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductVendorCreationRequest {
    private Long productId;
    private Long vendorId;
    private Integer quantity;
    private Double price;
    private Status status;

}
