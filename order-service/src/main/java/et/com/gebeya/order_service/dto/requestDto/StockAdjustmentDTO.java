package et.com.gebeya.order_service.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StockAdjustmentDTO {
    private Long productId;
    private int quantity;
}
