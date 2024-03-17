package et.com.gebeya.order_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import et.com.gebeya.order_service.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

//@EqualsAndHashCode(callSuper = true)
@EqualsAndHashCode( exclude = {"restaurant", "orderItems"})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"restaurant", "orderItems"})
@Builder


public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_on")
    private Instant createdOn;
    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_on")
    private Instant updatedOn;
    @ManyToOne
    @JoinColumn(name = "restaurant_Id",nullable = false, referencedColumnName = "id")
    private Restaurant restaurant;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private double totalPrice;
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> orderItems = new ArrayList<>();
    private String deliveryAddress;



}
