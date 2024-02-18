package et.com.gebeya.api_gateway.filter;


import et.com.gebeya.api_gateway.dto.TokenDto;
import et.com.gebeya.api_gateway.dto.ValidationResponseDto;
import et.com.gebeya.api_gateway.exception.HeaderNotFoundException;
import et.com.gebeya.api_gateway.exception.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {


    private final RouteValidator validator;
    private final WebClient.Builder webClientBuilder;


    public AuthenticationFilter(RouteValidator validator, WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.validator = validator;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new HeaderNotFoundException("Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                TokenDto token = TokenDto.builder().token(authHeader).build();
                return validateAuthorization(token)
                        .flatMap(response -> {
                            ServerHttpRequest mutatedHttpRequest = exchange.getRequest().mutate()
                                    .header("Role", response.getRole().toString())
                                    .header("RoleId",response.getRoleId().toString())
                                    .build();
                            ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedHttpRequest).build();
                            System.out.println(mutatedExchange);
                            return chain.filter(mutatedExchange);
                        })
                        .onErrorResume(error -> {
                            // Handle validation error
                            System.out.println("Error");
                            return chain.filter(exchange);

                        });
            }
            System.out.println(exchange);
            return chain.filter(exchange);
        });
    }



    public Mono<ValidationResponseDto> validateAuthorization(TokenDto token) {
        return webClientBuilder.build()
                .post()
                .uri("http://Auth-Service/api/v1/auth/validate")
                .header("Content-Type", "application/json")
                .body(Mono.just(token), TokenDto.class)
                .retrieve()
                .toEntity(ValidationResponseDto.class)
                .map(ResponseEntity::getBody)
                .onErrorResume(error -> {
                    System.out.println("erroryy");
                    return Mono.error(new UnAuthorizedException("UnAuthorized"));
                });
    }

    public static class Config {

    }
}
