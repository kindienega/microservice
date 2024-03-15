package et.com.gebeya.user_service.controller;

import et.com.gebeya.user_service.dto.requestDto.VendorRequestDto;
import et.com.gebeya.user_service.model.Vendor;
import et.com.gebeya.user_service.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/vendor")
public class VendorController {
private final VendorService vendorService;
    @PostMapping("/register")
    public ResponseEntity<VendorRequestDto> registerVendor(@RequestBody VendorRequestDto vendorRequestDto) {
        try {
            VendorRequestDto registeredDto = vendorService.vendorRegistration(vendorRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/get/{vendorId}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable Integer vendorId) {
        Vendor vendor = vendorService.getVendorById(vendorId);
        return ResponseEntity.ok(vendor);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vendor>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllActiveVendors();
        return ResponseEntity.ok(vendors);
    }


    @DeleteMapping("/delete/{vendorId}")
    public ResponseEntity<String> deleteVendor(@PathVariable Integer vendorId) {
        vendorService.deleteVendor(vendorId);
        return ResponseEntity.ok("Vendor deleted successfully");
    }
}
