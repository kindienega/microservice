package et.com.gebeya.api_gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor

public class ValidationResponseDto {
    private final Role role;
    private final Integer roleId;
}
