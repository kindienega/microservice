package et.com.gebeya.user_service.controller;

import et.com.gebeya.user_service.dto.requestDto.RestaurantRequestDto;
import et.com.gebeya.user_service.exception.RegistrationException;
import et.com.gebeya.user_service.model.Restaurant;
import et.com.gebeya.user_service.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/user")
public class RestaurantController {
    private final RestaurantService restaurantService;


    @PostMapping("/restaurant/register")
    public ResponseEntity<?> registerRestaurant(@RequestBody RestaurantRequestDto restaurantRequestDto) {
        try {
            RestaurantRequestDto registeredRestaurant = restaurantService.restaurantRegistration(restaurantRequestDto);
            return ResponseEntity.ok(registeredRestaurant);
        } catch (RegistrationException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("Internal server error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping("/restaurant/get/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable Integer id) {
        try {
            Restaurant restaurant = restaurantService.getRestaurantById(id);
            return ResponseEntity.ok(restaurant);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fetching restaurant");
        }
    }

    @GetMapping("/restaurant/all")
    public ResponseEntity<?> getAllActiveRestaurants() {
        try {
            List<Restaurant> restaurants = restaurantService.getAllActiveRestaurants();
            return ResponseEntity.ok(restaurants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fetching restaurants");
        }
    }

    @DeleteMapping("/restaurant/delete/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable("id") int id) {
        try {
            Restaurant deletedRestaurant = restaurantService.deleteRestaurant(id);
            return ResponseEntity.ok(deletedRestaurant);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting restaurant");
        }
    }

    @PutMapping("/restaurant/approve/{id}")
    public ResponseEntity<?> approveRestaurant(@PathVariable Integer id) {
        try {
            Restaurant approvedRestaurant = restaurantService.approveRestaurant(id);
            return ResponseEntity.ok(approvedRestaurant);
        } catch (RuntimeException e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while approving restaurant");
        }
    }

    // Exception handler for other exceptions not handled explicitly
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }}





