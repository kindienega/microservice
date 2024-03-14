package et.com.gebeya.user_service.dto.requestDto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AddressRequestDto {
    private String city;
    private String subCity;
    private String wereda;

}
