package et.com.gebeya.user_service.service;

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
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
     private UserUtil userUtil;
    @Transactional
    public RestaurantRequestDto restaurantRegistration(RestaurantRequestDto restaurantRequestDto) {
        try {

            Restaurant restaurant = MappingUtil.mapRestaurantDtoToModel(restaurantRequestDto);
            restaurant.setStatus(Status.PENDING);
            restaurant.setIsActive(true);
            restaurant = restaurantRepository.save(restaurant);

            Users users = Users.builder()
                    .userName(restaurantRequestDto.getUserName())
                    .password(passwordEncoder.encode(restaurantRequestDto.getPassword()))
                    .role(Role.RESTAURANT)
                    .status(Status.PENDING)
                    .email(restaurantRequestDto.getEmail())
                    .roleId(restaurant.getId())
                    .isActive(true)
                    .build();

            usersRepository.save(users);

            return restaurantRequestDto;
        } catch (DataIntegrityViolationException ex) {
            throw new RegistrationException("Failed to register restaurant due to data integrity violation", ex);
        } catch (Exception ex) {
            throw new RegistrationException("Failed to register restaurant", ex);
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


//    @Transactional
//    public Restaurant deleteRestaurant(Integer restaurantId) {
//        Restaurant restaurant=restaurantRepository.findById(restaurantId).orElseThrow(()->new ErrorHandler(HttpStatus.NOT_FOUND,"id can't be found"));
//           restaurantRepository.save(restaurant);
//
//
//    }

    public List<Restaurant> getRestaurantsByName(String name) {
        Specification<Restaurant> spec = RestaurantSpecification.getRestaurantByName(name);
        return restaurantRepository.findAll(spec);
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
            if (users == null) {
                throw new ErrorHandler(HttpStatus.BAD_REQUEST, "User with role ID " + id + " not found");
            }
            users.setStatus(Status.APPROVED);
            restaurantRepository.save(restaurant);
            usersRepository.save(users);
            return restaurant;
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to approve restaurant");
        }
    }




}
