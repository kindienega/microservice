package et.com.gebeya.user_service.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserCreationRequest {
    private String name;
    private String address;
    private String username;
    private String password;
    private String email;
    private String role;
}
