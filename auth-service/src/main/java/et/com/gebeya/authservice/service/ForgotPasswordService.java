package et.com.gebeya.authservice.service;

import et.com.gebeya.authservice.dto.requestdto.ForgotPasswordRequestDto;
import et.com.gebeya.authservice.exceptions.InvalidPhoneNumberException;
import et.com.gebeya.authservice.model.Users;
import et.com.gebeya.authservice.repository.UsersRepository;
import et.com.gebeya.authservice.util.SecureOtpGenerator;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {
    private final SmsService smsService;
    private final RedisService redisService;
    private final UsersRepository usersRepository;

    public void initiateForgotPassword(String phoneNumber) throws IOException {

            if(isValidPhoneNumber(phoneNumber)){
                throw new InvalidPhoneNumberException("invalid email format");
            }
            Users users=  usersRepository.findByPhoneNumber(phoneNumber);
            if (users==null){
                throw new NotFoundException("User with email"+phoneNumber+"not found");
            }
            String otp= SecureOtpGenerator.generateOTP();
            redisService.setOtp(phoneNumber,otp,10);
            ForgotPasswordRequestDto requestDto=ForgotPasswordRequestDto.builder().email(phoneNumber).otp(otp).build();
            String message="the password reset otp is: "+otp;
        smsService.sendSms(phoneNumber,"e80ad9d8-adf3-463f-80f4-7c4b39f7f164","",message);


    }
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Regex pattern for email validation
        String phoneRegex ="^\\+251\\d{9}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

}
