package et.com.gebeya.authservice.dto.requestdto;


import et.com.gebeya.authservice.enums.Role;
import et.com.gebeya.authservice.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRequest {
    private String userName;
    private String password;
    private String email;
private Integer roleId;
    private String phoneNumber;
    private Role role;
    private Status status;

}
