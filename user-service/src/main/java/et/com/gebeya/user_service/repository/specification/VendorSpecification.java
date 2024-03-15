package et.com.gebeya.user_service.repository.specification;

import et.com.gebeya.user_service.model.Restaurant;
import et.com.gebeya.user_service.model.Vendor;
import org.springframework.data.jpa.domain.Specification;

public class VendorSpecification {

    public static Specification<Vendor> getAllVendors()
    {
        return (((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("isActive"),false)));
    }
    public static Specification<Vendor>getVendorById(Integer id)
    {
        return (((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"),id)));
    }
}
