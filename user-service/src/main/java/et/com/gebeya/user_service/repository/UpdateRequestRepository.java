package et.com.gebeya.user_service.repository;

import et.com.gebeya.user_service.model.UpdateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdateRequestRepository extends JpaRepository<UpdateRequest, Long> {
}
