package et.com.gebeya.user_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneNumber extends BaseModel  {
    //todo make this unique
    @Column(length = 14)
    @Pattern(regexp = "^\\+251\\d{9}$")
    private String phoneNumber;

}