package et.com.gebeya.user_service.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AddAccountRequestDto {
    private String ownerName;
    private String email;
    private String username;
    private String password;
}
