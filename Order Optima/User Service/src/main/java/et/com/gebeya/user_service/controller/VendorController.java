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
    public ResponseEntity<VendorRequestDto> registerVendor(@RequestBody VendorRequestDto vendorRequestDto) {
        try {
            VendorRequestDto registeredDto = vendorService.vendorRegistration(vendorRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredDto);
        } catch (Exception e) {
            // to do :Handle exceptions appropriately, log errors, return meaningful error responses
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
