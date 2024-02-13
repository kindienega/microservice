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
    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = true)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Discount> discounts;
}