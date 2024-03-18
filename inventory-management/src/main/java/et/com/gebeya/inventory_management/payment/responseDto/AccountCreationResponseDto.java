package et.com.gebeya.inventory_management.payment.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountCreationResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Double balance;
    private String city;
}
