package et.com.gebeya.user_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static et.com.gebeya.user_service.util.Constant.ADD_ACCOUNT_TOPIC;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic addAccountTopic(){
        return TopicBuilder.name(ADD_ACCOUNT_TOPIC).build();
    }
}
