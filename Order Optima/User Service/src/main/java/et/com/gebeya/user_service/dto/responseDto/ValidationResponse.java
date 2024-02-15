package et.com.gebeya.user_service.dto.responseDto;


import et.com.gebeya.user_service.enums.Role;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class ValidationResponse {
    private final Role role;
    private final Integer roleId;
}
