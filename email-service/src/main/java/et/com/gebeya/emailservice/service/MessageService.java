package et.com.gebeya.emailservice.service;

import et.com.gebeya.emailservice.dto.AddAccountRequestDto;
import et.com.gebeya.emailservice.dto.ForgotPasswordRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static et.com.gebeya.emailservice.util.MessageTemplate.*;

@Component
@RequiredArgsConstructor
public class MessageService {
    private final EmailService emailService;

    public void addAccount(AddAccountRequestDto request){
        String message = addAccountMessage(request.getOwnerName(),request.getUsername(),request.getPassword());
        String subject = "NOTIFYING ACCOUNT CREATION";
        emailService.sendEmail(request.getEmail(),subject,message);
    }
    public void forgotPassword(ForgotPasswordRequestDto requestDto){
        String message=forgotPasswordMessage(requestDto.getOtp());
        String subject="OTP FOR RESETTING PASSWORD";
        emailService.sendEmail(requestDto.getEmail(), subject,message);
    }
}
