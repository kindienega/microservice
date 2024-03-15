package et.com.gebeya.user_service.service;

import et.com.gebeya.user_service.dto.requestDto.AddUserRequest;
import et.com.gebeya.user_service.dto.requestDto.RestaurantRequestDto;
import et.com.gebeya.user_service.enums.Role;
import et.com.gebeya.user_service.enums.Status;
import et.com.gebeya.user_service.exception.ErrorHandler;
import et.com.gebeya.user_service.exception.RegistrationException;
import et.com.gebeya.user_service.model.Restaurant;
//import et.com.gebeya.user_service.model.Users;
import et.com.gebeya.user_service.model.Users;
import et.com.gebeya.user_service.repository.RestaurantRepository;
import et.com.gebeya.user_service.repository.UsersRepository;
import et.com.gebeya.user_service.repository.specification.RestaurantSpecification;
import et.com.gebeya.user_service.util.MappingUtil;
import et.com.gebeya.user_service.util.UserUtil;
import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final SmsService smsService;
     private  final  UserUtil userUtil;
    @Transactional
    public RestaurantRequestDto restaurantRegistration(RestaurantRequestDto restaurantRequestDto) {
        try {

            Restaurant restaurant = MappingUtil.mapRestaurantDtoToModel(restaurantRequestDto);
            restaurantRequestDto.setId(restaurant.getId());
            restaurantRequestDto.setStatus(Status.PENDING);
            restaurant.setStatus(Status.PENDING);
            restaurant.setIsActive(true);
            restaurant = restaurantRepository.save(restaurant);

            Users users = Users.builder()
                    .userName(restaurantRequestDto.getUserName())
                    .password(passwordEncoder.encode(restaurantRequestDto.getPassword()))
                    .role(Role.RESTAURANT)
                    .status(Status.PENDING)
                    .phoneNumber(restaurant.getPhoneNumber().get(0).getPhoneNumber())
                    .roleId(restaurant.getId())
                    .isActive(true)
                    .build();

            usersRepository.save(users);
            String recipientPhoneNumber = restaurantRequestDto.getPhoneNumber().get(0).getPhoneNumber();
            String message = "You have successfully registered; please wait until our admins approve your account";
            smsService.sendSms(recipientPhoneNumber, "e80ad9d8-adf3-463f-80f4-7c4b39f7f164", "", message);
            return restaurantRequestDto;
        } catch (DataIntegrityViolationException ex) {
            throw new RegistrationException("Failed to register restaurant due to data integrity violation", ex);
        } catch (Exception ex) {
            throw new RegistrationException("Failed to register restaurant", ex);
        }
    }
//    private Restaurant createRestaurant(RestaurantRequestDto restaurantRequestDto) {
//        Restaurant restaurant = MappingUtil.mapRestaurantDtoToModel(restaurantRequestDto);
//        restaurant.setStatus(Status.PENDING);
//        restaurant.setIsActive(true);
//        return restaurantRepository.save(restaurant);
//    }
//
//    private String registerUserAndGetResponse(RestaurantRequestDto restaurantRequestDto) throws AuthException {
//        restaurantRequestDto.setRole(Role.RESTAURANT);
//        AddUserRequest addUserRequest = MappingUtil.mapCustomerToUser(restaurantRequestDto);
//        Mono<String> responseMono = authService.getAuthServiceResponseEntityMono(addUserRequest);
//        return responseMono.blockOptional().orElseThrow(() -> new AuthException("Error occurred during saving the user"));
//    }
//
//    private void validateAuthenticationResponse(String responseBody) throws AuthException {
//        if (responseBody == null || responseBody.isEmpty()) {
//            throw new AuthException("Empty response received from authentication service");
//        }
//    }
//
//    private void sendRegistrationConfirmationSms(RestaurantRequestDto restaurantRequestDto) throws IOException {
//        String recipientPhoneNumber = restaurantRequestDto.getPhoneNumber().get(0).getPhoneNumber();
//        String message = "You have successfully registered; please wait until our admins approve your account";
//        smsService.sendSms(recipientPhoneNumber, "e80ad9d8-adf3-463f-80f4-7c4b39f7f164", "", message);
//    }

    public Restaurant deleteRestaurant(Integer id)
    {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()->new RuntimeException(id + " can't be found"));
        restaurant.setIsActive(false);
        restaurant = restaurantRepository.save(restaurant);
        Users users = usersRepository.findByRoleId(restaurant.getId());
        userUtil.userDisabler(users);
        return restaurant;

    }

    public List<Restaurant> getAllActiveRestaurants() {
        Specification<Restaurant> spec = RestaurantSpecification.getAllRestaurants();
        return restaurantRepository.findAll(spec);
    }

    public Restaurant getRestaurantById(Integer id) {
        Specification<Restaurant> spec = RestaurantSpecification.getRestaurantById(id);
        Optional<Restaurant> restaurantOptional = restaurantRepository.findOne(spec);
        if (restaurantOptional.isPresent()) {
            return restaurantOptional.get();
        } else {
            throw new NotFoundException("Restaurant not found");
        }
    }
    public Restaurant approveRestaurant(Integer id) {
        try {
            Restaurant restaurant = restaurantRepository.findById(id)
                    .orElseThrow(() -> new ErrorHandler(HttpStatus.NOT_FOUND, id + " can't be found"));
            restaurant.setStatus(Status.APPROVED);

            Users users = usersRepository.findByRoleId(id);

            if(users!=null&& users.getRole()==Role.RESTAURANT){
                users.setStatus(Status.APPROVED);
                usersRepository.save(users);

            }
            String recipientPhoneNumber= restaurant.getPhoneNumber().get(0).getPhoneNumber();
            String message="your restaurant account has been approved. Welcome to order optima";
            smsService.sendSms(recipientPhoneNumber,"e80ad9d8-adf3-463f-80f4-7c4b39f7f164","",message);
            restaurantRepository.save(restaurant);
            return restaurant;
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to approve restaurant");
        }
    }




}
