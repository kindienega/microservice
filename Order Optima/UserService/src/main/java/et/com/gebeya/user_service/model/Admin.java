package et.com.gebeya.user_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Admin extends BaseModel{
    @Column(length = 255)
    @NotBlank(message = "FirstName is mandatory")
    private String firstName;
    @Column(length = 255)
    @NotBlank(message = "LastName is mandatory")
    private String lastName;
}

