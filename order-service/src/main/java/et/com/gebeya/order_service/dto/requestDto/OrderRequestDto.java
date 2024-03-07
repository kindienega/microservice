package et.com.gebeya.order_service.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

private Integer restaurantId;
    private List<OrderItemRequestDto> orderItems;
    private String deliveryAddress;

}
