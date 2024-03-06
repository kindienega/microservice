package et.com.gebeya.user_service.model;

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