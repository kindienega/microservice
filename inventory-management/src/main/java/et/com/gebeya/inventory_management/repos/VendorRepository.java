package et.com.gebeya.inventory_management.repos;

import et.com.gebeya.inventory_management.Models.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {
}
