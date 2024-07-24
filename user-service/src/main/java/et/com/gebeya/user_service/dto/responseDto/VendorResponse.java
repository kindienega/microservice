package et.com.gebeya.user_service.dto.responseDto;

import et.com.gebeya.user_service.dto.requestDto.AddressRequestDto;
import et.com.gebeya.user_service.dto.requestDto.PhoneNumberDto;
import et.com.gebeya.user_service.dto.requestDto.ProductId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorResponse {
    private Integer id;
    private String BusinessName;
    private String OwnerName;
    private String LicenseNumber;
    private List<AddressRequestDto> addresses;
    private String email;
    private List<PhoneNumberDto> phoneNumber;
    //private Set<Long> productId;
    private List<ProductId> productId;
}
