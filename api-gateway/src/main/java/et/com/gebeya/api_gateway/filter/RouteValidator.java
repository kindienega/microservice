package et.com.gebeya.api_gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/api/v1/products/order",
            //"api/v1/products",
            "/api/v1/products/*/stock",
            "/api/v1/products/category/*",
            "/api/v1/products/search",
            "/api/v1/user/restaurant/register"
            );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}