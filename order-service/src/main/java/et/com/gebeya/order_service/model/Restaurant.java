package et.com.gebeya.order_service.model;



import et.com.gebeya.order_service.Enum.Status;
import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Table(name = "restaurant")
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Restaurant extends BaseModel {
    @Column(length = 255)
    @NotBlank
    private String BusinessName;
    @Column(length = 255)
    @NotBlank
    private String OwnerName;
    @NotBlank
    @Column(unique = true)
    private String LicenseNumber;
    @Column(length = 255,unique = true)
    @Email(message = "Email is mandatory")
    private String email;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurantId")
    @NotEmpty
    private List<Address> addresses;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurantId")
    @NotEmpty
    private List<PhoneNumber> phoneNumber;
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;





}







