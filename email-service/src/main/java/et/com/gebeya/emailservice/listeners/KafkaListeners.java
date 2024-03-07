package et.com.gebeya.emailservice.listeners;

import et.com.gebeya.emailservice.dto.AddAccountRequestDto;
import et.com.gebeya.emailservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static et.com.gebeya.emailservice.util.Constant.ADD_ACCOUNT_TOPIC;

@Component
@RequiredArgsConstructor
public class KafkaListeners {
    private final MessageService messageService;
    @KafkaListener(topics = ADD_ACCOUNT_TOPIC, groupId = "group2",containerFactory = "addAccountListenerFactory")
    void addAccountListener(AddAccountRequestDto dto) {
        System.out.println(dto);
        messageService.addAccount(dto);
    }
}
