package et.com.gebeya.emailservice.dto;

import lombok.*;

@Data
@AllArgsConstructor

@NoArgsConstructor
@Builder
public class ForgotPasswordRequestDto {
    private String email;
    private String otp;
}
