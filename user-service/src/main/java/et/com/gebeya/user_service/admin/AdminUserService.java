package et.com.gebeya.user_service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;

    @Autowired
    public AdminUserService(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    public AdminUser createAdminUser(AdminUserCreationRequest request) {
        AdminUser adminUser = new AdminUser();
        adminUser.setName(request.getName());
        adminUser.setAddress(request.getAddress());
        adminUser.setUsername(request.getUsername());
        adminUser.setPassword(request.getPassword());
        adminUser.setEmail(request.getEmail());
        adminUser.setRole(request.getRole());

        return adminUserRepository.save(adminUser);
    }

    public List<AdminUser> getAllAdminUsers() {
        return adminUserRepository.findAll();
    }

    public AdminUser getAdminUserById(Long id) {
        return adminUserRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("AdminUser not found with id " + id));
    }

    public AdminUser updateAdminUser(Long id, AdminUser adminUserDetails) {
        AdminUser adminUser = getAdminUserById(id);
        adminUser.setUsername(adminUserDetails.getUsername());
        adminUser.setPassword(adminUserDetails.getPassword()); // Remember to hash the password
        adminUser.setEmail(adminUserDetails.getEmail());
        return adminUserRepository.save(adminUser);
    }

    public void deleteAdminUser(Long id) {
        adminUserRepository.deleteById(id);
    }
}