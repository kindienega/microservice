package et.com.gebeya.authservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static et.com.gebeya.authservice.util.Constant.FORGOT_PASSWORD_TOPIC;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic forgotPasswordTopic(){
        return TopicBuilder.name(FORGOT_PASSWORD_TOPIC).build();
    }
}
