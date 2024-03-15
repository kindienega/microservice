package et.com.gebeya.order_service.dto.requestDto;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OrderPaymentInfo {

    private Integer orderId;
    private Double totalPrice;


}
