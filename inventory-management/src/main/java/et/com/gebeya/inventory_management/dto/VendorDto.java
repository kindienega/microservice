package et.com.gebeya.inventory_management.dto;

import et.com.gebeya.inventory_management.Models.PhoneNumber;
import et.com.gebeya.inventory_management.Models.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class VendorDto {
    private Integer id;
    private String OwnerName;
    private String LicenseNumber;
    private List<PhoneNumber> phoneNumber;
    private List<Product> products;

    public VendorDto(Integer id, String ownerName, String licenseNumber, List<PhoneNumber> phoneNumber,
                     List<Product> products) {
        this.id = id;
        OwnerName = ownerName;
        LicenseNumber = licenseNumber;
        this.phoneNumber = phoneNumber;
        this.products = products;
    }
}
