package et.com.gebeya.emailservice.dto;

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
