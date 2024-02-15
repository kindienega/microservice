package et.com.gebeya.user_service.controller;

import et.com.gebeya.user_service.dto.requestDto.RestaurantRequestDto;
import et.com.gebeya.user_service.dto.requestDto.VendorRequestDto;
import et.com.gebeya.user_service.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user/vendor")
public class VendorController {
private final VendorService vendorService;
    @PostMapping("/register")
    public ResponseEntity<String> registerVendor(
            @RequestBody VendorRequestDto vendorRequestDto) {

        try {
            String registrationMessage = vendorService.vendorRegistration(vendorRequestDto);
            return ResponseEntity.ok(registrationMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }
}
