package et.com.gebeya.user_service.controller;

import et.com.gebeya.user_service.dto.requestDto.RestaurantRequestDto;


import et.com.gebeya.user_service.model.Restaurant;
import et.com.gebeya.user_service.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class RestaurantController {
    private final RestaurantService restaurantService;


    @PostMapping("/restaurant/register")
    public ResponseEntity<RestaurantRequestDto> registerRestaurant(@RequestBody RestaurantRequestDto restaurantRequestDto,
                                                                   @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            RestaurantRequestDto registeredRestaurant = restaurantService.restaurantRegistration(restaurantRequestDto, imageFile);
            return ResponseEntity.ok(registeredRestaurant);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/restaurant/{id}")
    public Restaurant getRestaurantById(@PathVariable Integer id) {
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping("/restaurant/all")
    public List<Restaurant> getAllActiveRestaurants() {
        return restaurantService.getAllActiveRestaurants();
    }

    @GetMapping("/restaurant/search")
    public List<Restaurant> getRestaurantsByName(@RequestParam String name) {
        return restaurantService.getRestaurantsByName(name);
    }

    @DeleteMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable("id") int id){
        return ResponseEntity.ok(restaurantService.deleteRestaurant(id));
    }


    @PutMapping("/restaurant/approve/{id}")
    public ResponseEntity<Restaurant> approveRestaurant(@PathVariable Integer id) {
        try {
            Restaurant approvedRestaurant = restaurantService.approveRestaurant(id);
            return ResponseEntity.ok(approvedRestaurant);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

    }}





