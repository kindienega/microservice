package et.com.gebeya.inventory_management.payment.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaymentDto {

    private String senderPhoneNumber;
    private String recipientPhoneNumber;
    private double paymentAmount;
    private double senderNewBalance;

    public PaymentDto(String senderPhoneNumber, String recipientPhoneNumber,
                      double paymentAmount, double senderNewBalance) {
        this.senderPhoneNumber = senderPhoneNumber;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.paymentAmount = paymentAmount;
        this.senderNewBalance = senderNewBalance;
    }
}
