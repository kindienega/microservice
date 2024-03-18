package et.com.gebeya.inventory_management.Models;

import et.com.gebeya.inventory_management.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
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
