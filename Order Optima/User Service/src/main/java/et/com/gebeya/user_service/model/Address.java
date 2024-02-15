package et.com.gebeya.user_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Table(name = "address")
@RequiredArgsConstructor
@Data
public class Address extends BaseModel {


    @Column(length = 255)
    @Email(message = "Email is mandatory")
    private String email;
    @Column(length = 255)
    @NotBlank(message = "City is mandatory")
    private String city;
    @Column(length = 255)
    @NotBlank(message = "SubCity is mandatory")
    private String subCity;
    @Column(length = 255)
    @NotBlank(message = "Wereda is mandatory")
    private String wereda;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "addressId")
    @NotEmpty
    private List<PhoneNumber> phoneNumber;
    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;
    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private Vendor vendor;

}

