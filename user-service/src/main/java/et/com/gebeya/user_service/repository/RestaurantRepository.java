package et.com.gebeya.user_service.repository;

import et.com.gebeya.user_service.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Integer>, JpaSpecificationExecutor<Restaurant> {
}
