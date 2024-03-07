package et.com.gebeya.order_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String inventoryId;
    private int quantity;
    private double price;
    private String Description;
    private String imageUrl;
    private String discount;
    private String calories;
    private String fat;
    private String protein;
    private String weight;
    private String size;
    private String volume;
    private String brands;

    @ManyToOne
    @JoinColumn(name = "catId", nullable = true)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Review> reviews;


//    public Product(Integer productId) {
//        this.id=productId.longValue();
//    }
}