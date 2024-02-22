package et.com.gebeya.inventory_management.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewDTO {
    private Long id;
    private Long productId;
    private Long userId;
    private int numberOutOfFive;
}
