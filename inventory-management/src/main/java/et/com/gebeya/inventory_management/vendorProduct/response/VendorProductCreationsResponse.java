package et.com.gebeya.inventory_management.vendorProduct.response;

import et.com.gebeya.inventory_management.vendorProduct.dto.ProductDto;
import et.com.gebeya.inventory_management.vendorProduct.dto.VendorDto;
import et.com.gebeya.inventory_management.vendorProduct.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VendorProductCreationsResponse {
    private ProductDto productDto;
    private VendorDto vendorDto;
    private Integer quantity;
    private Double price;
    private Status status;
}
