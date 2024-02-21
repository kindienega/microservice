package et.com.gebeya.authservice.controller;

import et.com.gebeya.authservice.dto.requestdto.UsersCredential;
import et.com.gebeya.authservice.dto.requestdto.ValidationRequest;
import et.com.gebeya.authservice.dto.responsedto.AuthenticationResponse;
import et.com.gebeya.authservice.dto.responsedto.ValidationResponse;
import et.com.gebeya.authservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
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
}