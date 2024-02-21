package et.com.gebeya.user_service.dto.requestDto;

import et.com.gebeya.user_service.enums.Role;
import et.com.gebeya.user_service.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddUserRequest {
    private String userName;
    private String password;
    private Status status;
    private Role role;
    private Integer roleId;

}
