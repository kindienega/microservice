package et.com.gebeya.inventory_management.vendorProduct;

import et.com.gebeya.inventory_management.Models.Product;
import et.com.gebeya.inventory_management.vendor.Vendor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class VendorProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "vendorId")
    private Vendor vendor;
    private Integer quantity;
    private Double price;
    private Status status;
}
