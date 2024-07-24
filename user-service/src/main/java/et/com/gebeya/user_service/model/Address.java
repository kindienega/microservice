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
    @NotBlank(message = "City is mandatory")
    private String city;
    @Column(length = 255)
    @NotBlank(message = "SubCity is mandatory")
    private String subCity;
    @Column(length = 255)
    @NotBlank(message = "Wereda is mandatory")
    private String wereda;
}

