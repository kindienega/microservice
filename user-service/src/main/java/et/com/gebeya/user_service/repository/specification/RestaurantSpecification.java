package et.com.gebeya.user_service.repository.specification;

import et.com.gebeya.user_service.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

public class RestaurantSpecification {
    public static Specification<Restaurant> getRestaurantByName(String name){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("business_name"),"%" + name + "%"));
    }
    public static Specification<Restaurant> getAllRestaurants()
    {
        return (((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("isActive"),false)));
    }
    public static Specification<Restaurant>getRestaurantById(Integer id)
    {
        return (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"),id)));
    }

}
