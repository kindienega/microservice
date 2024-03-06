package et.com.gebeya.authservice.controller;

import et.com.gebeya.authservice.dto.requestdto.UsersCredential;
import et.com.gebeya.authservice.dto.requestdto.ValidationRequest;
import et.com.gebeya.authservice.dto.responsedto.AuthenticationResponse;
import et.com.gebeya.authservice.dto.responsedto.ValidationResponse;
import et.com.gebeya.authservice.exceptions.InvalidEmailException;
import et.com.gebeya.authservice.service.AuthenticationService;
import et.com.gebeya.authservice.service.ForgotPasswordService;
import io.swagger.v3.oas.models.responses.ApiResponse;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    private final AuthenticationService authenticationService;
    private final ForgotPasswordService forgotPasswordService;
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
    @PostMapping("/forgot-password")
    public ResponseEntity<String> initiateForgotPassword(@RequestBody String email) {
        try {
            forgotPasswordService.initiateForgotPassword(email);
            return ResponseEntity.ok("Forgot password process initiated successfully.");
        } catch (InvalidEmailException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email format.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email " + email + " not found.");
        } catch (RuntimeException e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error initiating forgot password: " + e.getMessage());
        }
    }

}