package et.com.gebeya.user_service.dto.requestDto;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VendorRequestDto {
    private String BusinessName;
    private String OwnerName;
    private String LicenseNumber;
    private List<AddressRequestDto> addresses;
    private String email;
    private List<PhoneNumberDto> phoneNumber;
    private List<Long> productId;

//    public List<Long> getProductIds() {
//        return productId;
//    }

//    public void setProductIds(List<Long> productIds) {
//        this.productIds = productIds;
//    }
//    private String userName;
//    private String password;
}
