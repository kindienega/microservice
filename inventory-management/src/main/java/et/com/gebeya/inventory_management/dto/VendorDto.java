package et.com.gebeya.inventory_management.dto;

import et.com.gebeya.inventory_management.Models.PhoneNumber;
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
//    private List<ProductDTO> productDTOList;

    public VendorDto(Integer id, String ownerName, String licenseNumber,
                     List<PhoneNumber> phoneNumber) {
        this.id = id;
        OwnerName = ownerName;
        LicenseNumber = licenseNumber;
        this.phoneNumber = phoneNumber;
    }

}
