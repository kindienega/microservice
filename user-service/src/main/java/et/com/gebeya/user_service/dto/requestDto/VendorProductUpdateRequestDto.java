package et.com.gebeya.user_service.dto.requestDto;

import et.com.gebeya.user_service.enums.Status;
import et.com.gebeya.user_service.model.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VendorProductUpdateRequestDto {
    private String nameOfProduct;
    private int vendorQuantity;
    private Double vendorProductPrice;
    private List<PhoneNumber> phoneNumber;
    private Status status;
    private Integer vendorId;
}
