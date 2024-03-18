package et.com.gebeya.inventory_management.dto.request;

import et.com.gebeya.inventory_management.Models.PhoneNumber;
import et.com.gebeya.inventory_management.enums.Status;
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