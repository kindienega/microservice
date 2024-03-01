package et.com.gebeya.inventory_management.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OrderOptima")
                        .version("v1.0.0")
                        .description("It is a simple product CRUD controller endpoints")
                        .contact(new Contact()
                                .name("OrderOptima")
                                .email("orderoptima@gmail.com")
                                .url("http://8086/swagger/index.html")))
                .addSecurityItem(new SecurityRequirement().addList("jwtAuth"))
                .components(new Components()
                        .addSecuritySchemes("jwtAuth", new SecurityScheme()
                                .name("JWT Authentication")
                                .description("Enter JWT Bearer token **_only_**")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}