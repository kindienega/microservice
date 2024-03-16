package et.com.gebeya.user_service.dto.requestDto;

import et.com.gebeya.user_service.model.PhoneNumber;
import lombok.AllArgsConstructor;
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

    public VendorDto(Integer id, String ownerName, String licenseNumber,
                     List<PhoneNumber> phoneNumber) {
        this.id = id;
        OwnerName = ownerName;
        LicenseNumber = licenseNumber;
        this.phoneNumber = phoneNumber;
    }

}
