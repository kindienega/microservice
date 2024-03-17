package et.com.gebeya.inventory_management.payment.requestDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentRequestDto {
    private Long updateRequestId;
    private String senderPhoneNumber;

}
