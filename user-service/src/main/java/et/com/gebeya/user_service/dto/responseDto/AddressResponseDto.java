package et.com.gebeya.user_service.dto.responseDto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AddressResponseDto {
    private String email;
    private String city;
    private String subCity;
    private String wereda;
}
