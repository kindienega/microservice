package et.com.gebeya.user_service.repository;

import et.com.gebeya.user_service.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {
    Users findByRoleId(Integer id);


}
