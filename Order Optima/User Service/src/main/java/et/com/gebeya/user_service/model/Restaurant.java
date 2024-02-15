package et.com.gebeya.user_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import et.com.gebeya.user_service.enums.Role;
import et.com.gebeya.user_service.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    private String LicenseNumber;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Address> addresses;
    @Enumerated(EnumType.STRING)
    private Status status;






}







