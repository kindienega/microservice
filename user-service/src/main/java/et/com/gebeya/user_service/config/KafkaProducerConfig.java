package et.com.gebeya.user_service.config;

import et.com.gebeya.user_service.dto.requestDto.AddAccountRequestDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    public Map<String,Object> commonProps()
    {
        Map<String,Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    public Map<String,Object> dtoProducerConfig()
    {
        Map<String, Object> props = commonProps();
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }
    @Bean
    public ProducerFactory<String, AddAccountRequestDto> addAccountProducerFactory()
    {
        return new DefaultKafkaProducerFactory<>(dtoProducerConfig());
    }

    @Bean
    public KafkaTemplate<String,AddAccountRequestDto> addAccountKafkaTemplate()
    {
        return new KafkaTemplate<>(addAccountProducerFactory());
    }
}
