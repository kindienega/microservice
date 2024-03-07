package et.com.gebeya.inventory_management.vendorProduct.dto;

import et.com.gebeya.inventory_management.vendor.Vendor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VendorDto {
    private Long id;
    private String vendorName;

    public VendorDto(Vendor vendor) {
        this.id = vendor.getId();
        this.vendorName = vendor.getName();
    }
}
