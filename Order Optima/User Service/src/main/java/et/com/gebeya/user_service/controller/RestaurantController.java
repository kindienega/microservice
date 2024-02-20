package et.com.gebeya.user_service.controller;

import et.com.gebeya.user_service.dto.requestDto.RestaurantRequestDto;

import et.com.gebeya.user_service.dto.responseDto.RestaurantResponseDto;
import et.com.gebeya.user_service.model.Restaurant;
import et.com.gebeya.user_service.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;




@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping("/register")
    public ResponseEntity<RestaurantRequestDto> registerRestaurant(@RequestBody RestaurantRequestDto restaurantRequestDto) {
        try {
            RestaurantRequestDto registeredDto = restaurantService.restaurantRegistration(restaurantRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredDto);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Restaurant> approveRestaurant(@PathVariable Integer id) {
        try {
            Restaurant approvedRestaurant = restaurantService.approveRestaurant(id);
            return ResponseEntity.ok(approvedRestaurant);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

    }}





