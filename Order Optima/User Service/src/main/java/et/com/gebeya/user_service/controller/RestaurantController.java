package et.com.gebeya.user_service.controller;

import et.com.gebeya.user_service.dto.requestDto.RestaurantRequestDto;
import et.com.gebeya.user_service.dto.requestDto.UserCredential;
import et.com.gebeya.user_service.model.Restaurant;
import et.com.gebeya.user_service.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping("/register")
    public ResponseEntity<String> registerRestaurant(
            @RequestBody RestaurantRequestDto restaurantRequestDto) {

        try {
            String registrationMessage = restaurantService.restaurantRegistration(restaurantRequestDto);
            return ResponseEntity.ok(registrationMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }
    @PostMapping("/approve")
    public ResponseEntity<String> approveRestaurant() {
        try {

            restaurantService.approveRestaurant();
            return ResponseEntity.ok("Restaurant approved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to approve restaurant: " + e.getMessage());
        }
    }
}
