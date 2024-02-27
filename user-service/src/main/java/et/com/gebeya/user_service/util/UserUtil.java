package et.com.gebeya.user_service.util;


import et.com.gebeya.user_service.dto.requestDto.UserDto;
import et.com.gebeya.user_service.enums.Role;
import et.com.gebeya.user_service.enums.Status;
import et.com.gebeya.user_service.model.Users;
import et.com.gebeya.user_service.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class UserUtil {
   private final UsersRepository usersRepository;
       private final PasswordEncoder passwordEncoder;

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

    public void createUser(String businessName, String ownerName, Integer id, Role role, Status status,String email ) {
        String username = businessName.toLowerCase() + "." + ownerName.toLowerCase().charAt(0);
        String password = String.valueOf(randomStringGenerator());
        Users users = Users.builder()
                .userName(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .email(email)
                .isActive(true)
                .status(status)
                .roleId(id)
                .build();

        usersRepository.save(users);

        UserDto dto = UserDto.builder()
                .userName(username)
                .password(password)
                .role(role.name())
                .email(users.getEmail())
                .build();


    }

    public void userDisabler(Users users)
    {

        users.setIsActive(false);
        usersRepository.save(users);
    }
}
