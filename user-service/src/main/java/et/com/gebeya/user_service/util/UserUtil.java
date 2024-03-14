package et.com.gebeya.user_service.util;


import et.com.gebeya.user_service.dto.requestDto.AddUserRequest;
import et.com.gebeya.user_service.enums.Role;
import et.com.gebeya.user_service.enums.Status;
import et.com.gebeya.user_service.model.Users;
import et.com.gebeya.user_service.repository.UsersRepository;
import et.com.gebeya.user_service.service.AuthService;
import et.com.gebeya.user_service.service.SmsService;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Random;



@Component
@RequiredArgsConstructor
public class UserUtil {
   private final UsersRepository usersRepository;
       private final PasswordEncoder passwordEncoder;
       private final AuthService authService;
       private final SmsService smsService;


    private static StringBuilder randomStringGenerator() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        final int DEFAULT_LENGTH = 12;
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < DEFAULT_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(index);
            stringBuilder.append(randomChar);
        }
        return stringBuilder;
    }

    public void createUser(String businessName, String ownerName, Integer id, Role role, Status status, String email , String phoneNumber) throws AuthException, IOException {
        String username = businessName.toLowerCase() + "." + ownerName.toLowerCase().charAt(0);
        String password = String.valueOf(randomStringGenerator());
        AddUserRequest userRequest=new AddUserRequest();
        userRequest.setUserName(username);
        userRequest.setPhoneNumber(phoneNumber);
        userRequest.setRole(role);
        userRequest.setStatus(status);
        userRequest.setPassword(password);
        userRequest.setRoleId(id);
        Mono<String> responseMono=authService.getAuthServiceResponseEntityMono(userRequest);
        String responseBody=responseMono.blockOptional().orElseThrow(()->new AuthException("Error occurred during saving the user"));
        if (responseBody==null||responseBody.isEmpty()){
            throw new AuthException("Empty response received from authentication service" );
        }

//        Users users = Users.builder()
//                .userName(username)
//                .password(passwordEncoder.encode(password))
//                .role(role)
//                .isActive(true)
//                .status(status)
//                .roleId(id)
//                .build();
//        usersRepository.save(users);

        String message="The username is :"+userRequest.getUserName()+"the password is"+userRequest.getPassword();
        smsService.sendSms(phoneNumber,"e80ad9d8-adf3-463f-80f4-7c4b39f7f164","",message);
    }

    public void userDisabler(Users users)
    {

        users.setIsActive(false);
        usersRepository.save(users);
    }
}
