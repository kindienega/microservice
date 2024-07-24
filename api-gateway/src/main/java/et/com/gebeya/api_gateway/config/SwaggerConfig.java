package et.com.gebeya.api_gateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OrderOptima ")
                        .version("v1.0.0")
                        .description("It is a simple product crud controller endpoints")
                        .contact(new Contact()
                                .name("orderOptima")
                                .email("orderoptima@gmail.com")
                                .url("http:/8086/swagger/index.html"))
                );
    }
}