package et.com.gebeya.order_service.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class OrderItemResponseDto {
    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
}
