package et.com.gebeya.user_service.model;

import et.com.gebeya.user_service.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "vendor")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Vendor extends BaseModel {

    @Column(length = 255)
    @NotBlank
    private String BusinessName;
    @Column(length = 255)
    @NotBlank
    private String OwnerName;
    @NotBlank
    @Column(unique = true)
    private String LicenseNumber;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "vendorId")
    @NotEmpty
    private List<Address> addresses;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "vendorId")
    @NotEmpty
    private List<PhoneNumber> phoneNumber;
    @Column(length = 255,unique = true)
    @Email(message = "Email is mandatory")
    private String email;


}
