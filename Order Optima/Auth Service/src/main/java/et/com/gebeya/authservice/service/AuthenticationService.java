package et.com.gebeya.authservice.service;

import et.com.gebeya.authservice.dto.requestdto.UsersCredential;
import et.com.gebeya.authservice.dto.requestdto.ValidationRequest;
import et.com.gebeya.authservice.dto.responsedto.AuthenticationResponse;
import et.com.gebeya.authservice.dto.responsedto.ValidationResponse;
import et.com.gebeya.authservice.enums.Role;
import et.com.gebeya.authservice.model.Users;
import et.com.gebeya.authservice.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsersRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsersService usersService;
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody UsersCredential usersCredential) {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usersCredential.getUserName(), usersCredential.getPassword()));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e.getMessage());
        }

        Users user = userRepository.findFirstByUserName(usersCredential.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid user name or password"));
        // Check if the user is approved or if the status is pending
        if (!user.isApproved()&&user.getRole()!= Role.ADMIN) {
            throw new RuntimeException("User is not approved or status is pending");
        }

        String jwt = jwtService.generateToken(user);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token(jwt)
                .role(user.getRole())
                .build();

        return ResponseEntity.ok(response);
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
