package et.com.gebeya.inventory_management.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfiguration {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dw60meomq",
                      "api_key", "619347524538979",
                      "api_secret", "6JpkcEsGx7jxv02Tr0tg4mQON0g",
                      "secure", true));
    }
}

