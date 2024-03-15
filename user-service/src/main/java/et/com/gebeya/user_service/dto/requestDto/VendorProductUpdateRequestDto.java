package et.com.gebeya.user_service.dto.requestDto;

import et.com.gebeya.user_service.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VendorProductUpdateRequestDto {
    private String nameOfProduct;
    private int vendorQuantity;
    private Double vendorProductPrice;
    private Status status;
}
