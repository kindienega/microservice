package et.com.gebeya.order_service.repository.Specification;

import et.com.gebeya.order_service.model.Orders;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {
    public static Specification<Orders> findByRestaurantId(Integer restaurantId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("restaurant").get("id"), restaurantId);
        };
    }
}
