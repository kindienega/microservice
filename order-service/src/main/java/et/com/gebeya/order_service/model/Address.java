package et.com.gebeya.order_service.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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

