package et.com.gebeya.user_service.dto.responseDto;

import et.com.gebeya.user_service.dto.requestDto.AddressRequestDto;
import et.com.gebeya.user_service.dto.requestDto.PhoneNumberDto;
import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RestaurantResponseDto {
    private String BusinessName;
    private String OwnerName;
    private String LicenseNumber;
    private List<AddressResponseDto> addresses;
    private List<PhoneNumberResponseDto> phoneNumber;
}
