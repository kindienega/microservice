package et.com.gebeya.authservice.dto.responsedto;

import et.com.gebeya.authservice.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationResponse {
    private final Role role;
    private final Integer roleId;
}
