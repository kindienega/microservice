package et.com.gebeya.user_service.service;

import et.com.gebeya.user_service.dto.requestDto.AddUserRequest;
import et.com.gebeya.user_service.dto.responseDto.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

    @RequiredArgsConstructor
    @Slf4j
    @Service
    public class AuthService {
        private final WebClient.Builder webclientBuilder;

        public Mono<ResponseEntity<AuthResponse>> getAuthServiceResponseEntityMono(AddUserRequest addUserRequest) {
            return webclientBuilder.build()
                    .post()
                    .uri("http://AUTH-SERVICE/api/v1/auth/addUser")
                    .body(Mono.just(addUserRequest), AddUserRequest.class)
                    .retrieve()
                    .toEntity(AuthResponse.class)
                    .onErrorResume(throwable -> {
                        // Handle the error here
                        log.error("Error occurred while calling authentication service: {}", throwable.getMessage());
                        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponse("Error occurred while calling authentication service")));
                    });
        }


    }
