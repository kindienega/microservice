package et.com.gebeya.user_service.model;

import et.com.gebeya.user_service.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class UpdateRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int productQuantity;
    private Double vendorProductPrice;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<PhoneNumber> phoneNumber;
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "id", nullable = false)
    private Vendor vendor;

}


