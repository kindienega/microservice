package et.com.gebeya.user_service.dto.requestDto;

import et.com.gebeya.user_service.enums.Role;
import et.com.gebeya.user_service.enums.Status;
import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VendorRequestDto {
    private String BusinessName;
    private String OwnerName;
    private String LicenseNumber;
    private List<AddressRequestDto> addresses;
    private String userName;
    private String password;
    private List<PhoneNumberDto> phoneNumber;
}
