package et.com.gebeya.inventory_management.payment.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferDto {
    private String senderPhoneNumber;
    private String recipientPhoneNumber;
    private Double transferAmount;
    private Double senderNewBalance;
    private Double recipientNewBalance;
}
