package et.com.gebeya.authservice.controller;

import et.com.gebeya.authservice.dto.requestdto.AddUserRequest;
import et.com.gebeya.authservice.dto.requestdto.UsersCredential;
import et.com.gebeya.authservice.dto.requestdto.ValidationRequest;
import et.com.gebeya.authservice.dto.responsedto.AuthResponse;
import et.com.gebeya.authservice.dto.responsedto.AuthenticationResponse;
import et.com.gebeya.authservice.dto.responsedto.ValidationResponse;
import et.com.gebeya.authservice.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/validate")
    public ResponseEntity<ValidationResponse> validate(@RequestBody ValidationRequest request)
    {
        return authenticationService.validate(request);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UsersCredential credential)
    {

        return authenticationService.signIn(credential);
    }


    @Hidden
    @PostMapping("/addUser")
    public ResponseEntity<AuthResponse> addUser(@RequestBody AddUserRequest dto) {
        try {
            ResponseEntity<AuthResponse> response = authenticationService.addUser(dto);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}