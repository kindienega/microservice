package et.com.gebeya.user_service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @Autowired
    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @PostMapping
    public ResponseEntity<AdminUser> createAdminUser(@RequestBody AdminUserCreationRequest request) {
        AdminUser newAdminUser = adminUserService.createAdminUser(request);
        return new ResponseEntity<>(newAdminUser, HttpStatus.CREATED);
    }

    @GetMapping
    public List<AdminUser> getAllAdminUsers() {
        return adminUserService.getAllAdminUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminUser> getAdminUserById(@PathVariable Long id) {
        AdminUser adminUser = adminUserService.getAdminUserById(id);
        return ResponseEntity.ok(adminUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminUser> updateAdminUser(@PathVariable Long id, @RequestBody AdminUser adminUserDetails) {
        AdminUser updatedAdminUser = adminUserService.updateAdminUser(id, adminUserDetails);
        return ResponseEntity.ok(updatedAdminUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAdminUser(@PathVariable Long id) {
        adminUserService.deleteAdminUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}