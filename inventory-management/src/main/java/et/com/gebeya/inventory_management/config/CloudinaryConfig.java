package et.com.gebeya.inventory_management.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class CloudinaryConfig {
//
//    private final String CLOUD_NAME = "dw60meomq";
//    private final String API_KEY = "619347524538979";
//    private final String API_SECRET = "6JpkcEsGx7jxv02Tr0tg4mQON0g";
    @Bean
    public Cloudinary ImageUpload() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dw60meomq",
                       "api_key", "619347524538979",
                       "api_secret", "6JpkcEsGx7jxv02Tr0tg4mQON0g",
                       "secure", true));
    }
}
