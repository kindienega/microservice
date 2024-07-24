package et.com.gebeya.inventory_management.payment.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PaymentResultDto {
    private String senderPhoneNumber;
    private String recipientPhoneNumber;
    private double paymentAmount;
    private double senderNewBalance;
}
