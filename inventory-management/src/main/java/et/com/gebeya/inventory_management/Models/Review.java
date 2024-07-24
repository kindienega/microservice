package et.com.gebeya.inventory_management.Models;

import jakarta.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;
    private Long userId;
    private int numberOutOfFive;
}