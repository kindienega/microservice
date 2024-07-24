package et.com.gebeya.authservice.service;

import et.com.gebeya.authservice.dto.requestdto.AddUserRequest;
import et.com.gebeya.authservice.dto.requestdto.ChangePassword;
import et.com.gebeya.authservice.dto.requestdto.UsersCredential;
import et.com.gebeya.authservice.dto.requestdto.ValidationRequest;
import et.com.gebeya.authservice.dto.responsedto.AuthResponse;
import et.com.gebeya.authservice.dto.responsedto.AuthenticationResponse;
import et.com.gebeya.authservice.dto.responsedto.ValidationResponse;
import et.com.gebeya.authservice.enums.Role;
import et.com.gebeya.authservice.enums.Status;
import et.com.gebeya.authservice.exceptions.PasswordChangeException;
import et.com.gebeya.authservice.model.Users;
import et.com.gebeya.authservice.repository.UsersRepository;
import jakarta.security.auth.message.AuthException;
import jakarta.ws.rs.ServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsersRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody UsersCredential usersCredential) {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usersCredential.getUserName(), usersCredential.getPassword()));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e.getMessage());
        }

        Users user = userRepository.findFirstByUserName(usersCredential.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid user name or password"));




        if (!user.isApproved()&&user.getRole()== Role.RESTAURANT) {
            throw new RuntimeException("User is not approved or status is pending");
        }


        String jwt = jwtService.generateToken(user);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token(jwt)
                .role(user.getRole())
                .roleId(user.getRoleId())
                .build();

        return ResponseEntity.ok(response);
    }



    public ResponseEntity<AuthResponse> addUser(AddUserRequest dto){
        try {
            Users users=Users.builder()
                    .userName(dto.getUserName())
                    .isActive(true)
                    .role(dto.getRole())
                    .roleId(dto.getRoleId())
                    .status(dto.getStatus())
                    .phoneNumber(dto.getPhoneNumber())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .build();
            userRepository.save(users);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            // Log the exception for further analysis
           log.error(ex.getMessage(),ex);
            // Return an error response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }







    public ResponseEntity<ValidationResponse> validate(ValidationRequest validationRequest)
    {
        final String userName;
        userName = jwtService.extractUserName(validationRequest.getToken());
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        if (StringUtils.isNotEmpty(userName)) {
            Users users = usersService.loadUserByUsername(userName);
            if (jwtService.isTokenValid(validationRequest.getToken(), users)) {
                ValidationResponse response = ValidationResponse.builder()
                        .role(users.getRole())
                        .roleId(users.getRoleId())
                        .build();
                System.out.println(response);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        System.out.println ("HttpStatus.UNAUTHORIZED)");
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


}
