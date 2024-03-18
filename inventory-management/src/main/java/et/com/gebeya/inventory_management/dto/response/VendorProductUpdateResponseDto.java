package et.com.gebeya.inventory_management.dto.response;

import et.com.gebeya.inventory_management.dto.VendorDto;
import et.com.gebeya.inventory_management.enums.Status;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class VendorProductUpdateResponseDto {
    private Long id;
    private String nameOfProduct;
    private int vendorQuantity;
    private Double vendorProductPrice;
    @ManyToOne
    private @Pattern(regexp = "^\\+251\\d{9}$") String phoneNumber;
    private Status status;
    private VendorDto vendor;

    public @Pattern(regexp = "^\\+251\\d{9}$") String setPhoneNumber(@Pattern(regexp = "^\\+251\\d{9}$") String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return phoneNumber;
    }
}