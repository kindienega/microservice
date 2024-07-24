package et.com.gebeya.inventory_management.repos;

import et.com.gebeya.inventory_management.Models.UpdateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateRequestRepository extends JpaRepository<UpdateRequest, Long> {
}
