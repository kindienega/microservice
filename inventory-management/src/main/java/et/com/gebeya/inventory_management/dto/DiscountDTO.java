package et.com.gebeya.inventory_management.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DiscountDTO {
    private Long id;
    private Long productId;
    private double discountPercent;
}
