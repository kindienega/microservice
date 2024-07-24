package et.com.gebeya.order_service.dto.responseDto;

import et.com.gebeya.order_service.Enum.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class OrderResponseDto {
    private Integer orderId;
    private double totalPrice;
    private OrderStatus orderStatus;



}
