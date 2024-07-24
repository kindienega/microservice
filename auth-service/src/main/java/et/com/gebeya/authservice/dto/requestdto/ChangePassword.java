package et.com.gebeya.authservice.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChangePassword {
    private String userName;
    private String currentPassword;
    private String newPassword;
}
