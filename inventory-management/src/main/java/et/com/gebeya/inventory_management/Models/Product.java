package et.com.gebeya.inventory_management.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String inventoryId;
    private int quantity;
    private Double price;
    private String Description;
    private String productDetail;
    private String imageUrl;
    private String discount;
    //private MultipartFile photo;
    @ManyToOne
    @JoinColumn(name = "catId", nullable = true)
    private Category category;
//    @ManyToOne
//    @JoinColumn(name = "vendorId", nullable = true)
//    private Vendor vendor;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Review> reviews;
    //private Long categoryId;

}