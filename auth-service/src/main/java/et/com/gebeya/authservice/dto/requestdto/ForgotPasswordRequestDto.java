package et.com.gebeya.authservice.dto.requestdto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForgotPasswordRequestDto {
    private String phoneNumber;
    private String otp;
    private String newPassword;
}
