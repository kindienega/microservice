package et.com.gebeya.inventory_management.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

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
    private String imageUrl;
    private String discount;
    private String calories;
    private String fat;
    private String protein;
    private String weight;
    private String size;
    private String volume;
    private String brands;
    //private MultipartFile photo;
    @ManyToOne
    @JoinColumn(name = "catId", nullable = true)
    private Category category;
//    @ManyToOne
//    @JoinColumn(name = "adminId", nullable = true)
//    private Admins admin;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Review> reviews;
    //private Long categoryId;

}