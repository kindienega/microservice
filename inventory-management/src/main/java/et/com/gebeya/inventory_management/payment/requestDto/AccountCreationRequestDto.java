package et.com.gebeya.inventory_management.payment.requestDto;

import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountCreationRequestDto {
    @Valid
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String firstName;
    private String lastName;
    @NotNull(message = "phone number cannot be null")
    @NotBlank(message = " phone number not blank")
    @Size(min = 13, max = 13, message = " phone number must be exactly 13 including +251 ")
    private String phoneNumber;
    private Double balance;
    private String city;
}
