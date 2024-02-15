package et.com.gebeya.user_service.dto.requestDto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import et.com.gebeya.user_service.enums.Role;
import et.com.gebeya.user_service.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RestaurantRequestDto {
    private String BusinessName;
    private String OwnerName;
    private String LicenseNumber;
    private List<AddressRequestDto> addresses;
    private String userName;
    private String password;
    private Status status;
    private Role role;
    private Integer roleId;




}
