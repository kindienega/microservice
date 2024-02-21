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
import et.com.gebeya.user_service.util.MappingUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    // private final CloudinaryService cloudinaryService;
    @Transactional
    public RestaurantRequestDto restaurantRegistration(RestaurantRequestDto restaurantRequestDto) {
        try {
            // String imageUrl=cloudinaryService.uploadImage(imageFile);
            Restaurant restaurant = MappingUtil.mapRestaurantDtoToModel(restaurantRequestDto);
            restaurant.setStatus(Status.PENDING);
            restaurant.setIsActive(true);
            // restaurant.setProfilePictureUrl(imageUrl);
            restaurant = restaurantRepository.save(restaurant);

            Users users = Users.builder()
                    .userName(restaurantRequestDto.getUserName())
                    .password(passwordEncoder.encode(restaurantRequestDto.getPassword()))
                    .role(Role.RESTAURANT)
                    .status(Status.PENDING)
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
