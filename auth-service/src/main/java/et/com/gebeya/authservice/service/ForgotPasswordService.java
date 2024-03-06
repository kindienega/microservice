package et.com.gebeya.authservice.service;

import et.com.gebeya.authservice.dto.requestdto.ForgotPasswordRequestDto;
import et.com.gebeya.authservice.exceptions.InvalidEmailException;
import et.com.gebeya.authservice.model.Users;
import et.com.gebeya.authservice.repository.UsersRepository;
import et.com.gebeya.authservice.util.SecureOtpGenerator;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static et.com.gebeya.authservice.util.Constant.FORGOT_PASSWORD_TOPIC;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {
    private final KafkaTemplate<String, ForgotPasswordRequestDto> forgotPasswordKafkaTemplate;
    private final RedisService redisService;
    private final UsersRepository usersRepository;

    public void initiateForgotPassword(String email){
        try{
            if(isValidEmail(email)){
                throw new InvalidEmailException("invalid email format");
            }
            Users users=usersRepository.findByEmail(email);
            if (users==null){
                throw new NotFoundException("User with email"+email+"not found");
            }
            String otp= SecureOtpGenerator.generateOTP();
            redisService.setOtp(email,otp,10);
            ForgotPasswordRequestDto requestDto=ForgotPasswordRequestDto.builder().email(email).otp(otp).build();
            forgotPasswordKafkaTemplate.send(FORGOT_PASSWORD_TOPIC,requestDto);

        }catch (KafkaProducerException e){
            throw new RuntimeException("Error Initiating forgot password: "+e.getMessage());
        }
    }
    private boolean isValidEmail(String email) {
        // Regex pattern for email validation
        String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
