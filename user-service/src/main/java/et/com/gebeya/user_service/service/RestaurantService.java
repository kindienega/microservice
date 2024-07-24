package et.com.gebeya.user_service.service;

import et.com.gebeya.user_service.dto.requestDto.AddUserRequest;
import et.com.gebeya.user_service.dto.requestDto.RestaurantRequestDto;
import et.com.gebeya.user_service.dto.responseDto.AuthResponse;
import et.com.gebeya.user_service.enums.Role;
import et.com.gebeya.user_service.enums.Status;
import et.com.gebeya.user_service.exception.ErrorHandler;
import et.com.gebeya.user_service.exception.RegistrationException;
import et.com.gebeya.user_service.model.Restaurant;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UsersRepository usersRepository;
    private final SmsService smsService;
     private  final  UserUtil userUtil;
     private final AuthService authService;
     @Value("${afroMessage.identifier}")
     public String identifier;


    @Transactional
    public RestaurantRequestDto restaurantRegistration(RestaurantRequestDto restaurantRequestDto) {
        Integer rId;
        try {

            Restaurant restaurant = MappingUtil.mapRestaurantDtoToModel(restaurantRequestDto);
            restaurant.setStatus(Status.PENDING);
            restaurant.setIsActive(true);
            restaurant = restaurantRepository.save(restaurant);
            rId=restaurant.getId();
            restaurantRequestDto.setRole(Role.RESTAURANT);
            restaurantRequestDto.setId(rId);
            restaurantRequestDto.setStatus(Status.PENDING);
            AddUserRequest addUserRequest=MappingUtil.mapRestaurantToUser(restaurantRequestDto);
            Mono<ResponseEntity<AuthResponse>> responseMono=authService.getAuthServiceResponseEntityMono(addUserRequest);

            ResponseEntity<AuthResponse> responseEntity = responseMono
                    .blockOptional()
                    .orElseThrow(() -> new AuthException("Error occurred while saving the user"));




            String recipientPhoneNumber= restaurantRequestDto.getPhoneNumber().get(0).getPhoneNumber();
            String message="Ypu have successfully registered; please wait until our admins approve your account";
            smsService.sendSms(recipientPhoneNumber,identifier,"",message);
            return restaurantRequestDto;
        }catch (DataIntegrityViolationException ex) {
            throw new RegistrationException("Failed to register restaurant due to data integrity violation", ex);
        } catch (AuthException ex) {
            log.error(ex.getMessage(),ex);
            throw new RegistrationException("Failed to register restaurant due to authentication error: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new RegistrationException("Failed to register restaurant: " + ex.getMessage(), ex);
        }
    }
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
            smsService.sendSms(recipientPhoneNumber,identifier,"",message);
            restaurantRepository.save(restaurant);
            return restaurant;
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to approve restaurant");
        }
    }




}
