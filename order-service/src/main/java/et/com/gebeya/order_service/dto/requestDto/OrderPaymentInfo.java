package et.com.gebeya.order_service.dto.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPaymentInfo {

    private Integer orderId;
    private Double totalPrice;

    public OrderPaymentInfo(Integer orderId, double totalPrice) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }
}
