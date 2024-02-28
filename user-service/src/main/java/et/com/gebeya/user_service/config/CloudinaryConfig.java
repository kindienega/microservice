 package et.com.gebeya.user_service.config;

 import com.cloudinary.Cloudinary;
 import com.cloudinary.utils.ObjectUtils;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;

 import java.util.HashMap;
 import java.util.Map;

 @Configuration
 public class CloudinaryConfig {

     @Bean
     public Cloudinary cloudinary() {
         return new Cloudinary(ObjectUtils.asMap(
                 "cloud_name", "dw60meomq",
                 "api_key", "619347524538979",
                 "api_secret", "6JpkcEsGx7jxv02Tr0tg4mQON0g",
                 "secure", true));
     }
 }
