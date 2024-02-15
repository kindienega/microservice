package et.com.gebeya.user_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    private String LicenseNumber;
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Address> addresses;



}
