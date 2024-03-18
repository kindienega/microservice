package et.com.gebeya.inventory_management.payment.repository;

import et.com.gebeya.inventory_management.payment.model.MpesaAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MpesaAccountRepository extends JpaRepository<MpesaAccount, Long> {
    Optional<MpesaAccount> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);
}
