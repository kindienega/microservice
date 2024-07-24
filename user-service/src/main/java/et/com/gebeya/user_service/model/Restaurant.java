package et.com.gebeya.user_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import et.com.gebeya.user_service.enums.Role;
import et.com.gebeya.user_service.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Table(name = "restaurant")
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Restaurant extends BaseModel{

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
   





}







