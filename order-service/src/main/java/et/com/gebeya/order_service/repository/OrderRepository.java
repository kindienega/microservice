package et.com.gebeya.order_service.repository;

import et.com.gebeya.order_service.Enum.OrderStatus;
import et.com.gebeya.order_service.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer>, JpaSpecificationExecutor<Orders>{
    List<Orders> findByOrderStatusNot(OrderStatus status);

    List<Orders> findByRestaurantId(Integer restaurant_id);
}
