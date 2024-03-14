package et.com.gebeya.user_service.service;

import et.com.gebeya.user_service.dto.requestDto.AddUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final WebClient.Builder webclientBuilder;
    public Mono<String> getAuthServiceResponseEntityMono(AddUserRequest addUserRequest){
        return webclientBuilder.build()
                .post()
                .uri("http://AUTH-SERVICE/api/v1/auth/addUser")
                .body(Mono.just(addUserRequest), AddUserRequest.class)
                .retrieve()
                .bodyToMono(String.class);
    }
}
