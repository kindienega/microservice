package et.com.gebeya.emailservice.config;

import et.com.gebeya.emailservice.dto.AddAccountRequestDto;
import et.com.gebeya.emailservice.dto.ForgotPasswordRequestDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        // private final String bootstrapServer = "http://localhost:9092";
       // String bootstrapServer = "http://broker:9092";
        String bootstrapServer = "localhost:9092";
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }
    @Bean
    public ConsumerFactory<String, String> stringConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
    factory(ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        return factory;
    }
    @Bean
    public ConsumerFactory<String, AddAccountRequestDto> addAccountConsumerFactory() {
        JsonDeserializer<AddAccountRequestDto> jsonDeserializer = new JsonDeserializer<>(AddAccountRequestDto.class, false);
        jsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new ErrorHandlingDeserializer<>(jsonDeserializer));
    }


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, AddAccountRequestDto>> addAccountListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AddAccountRequestDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(addAccountConsumerFactory());
        return factory;
    }
  @Bean
    public ConsumerFactory<String, ForgotPasswordRequestDto> forgotPasswordConsumerFactory() {
        JsonDeserializer<ForgotPasswordRequestDto> jsonDeserializer = new JsonDeserializer<>(ForgotPasswordRequestDto.class, false);
        jsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new ErrorHandlingDeserializer<>(jsonDeserializer));
    }


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, ForgotPasswordRequestDto>> forgotPasswordListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ForgotPasswordRequestDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(forgotPasswordConsumerFactory());
        return factory;
    }

}
