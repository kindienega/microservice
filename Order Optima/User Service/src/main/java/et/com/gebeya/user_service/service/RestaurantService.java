package et.com.gebeya.user_service.service;

import et.com.gebeya.user_service.dto.requestDto.AddUserRequest;
import et.com.gebeya.user_service.dto.requestDto.RestaurantRequestDto;
import et.com.gebeya.user_service.dto.requestDto.UserCredential;
import et.com.gebeya.user_service.dto.requestDto.UserDto;
import et.com.gebeya.user_service.dto.responseDto.AddUserResponse;
import et.com.gebeya.user_service.enums.Role;
import et.com.gebeya.user_service.enums.Status;
import et.com.gebeya.user_service.model.Restaurant;
//import et.com.gebeya.user_service.model.Users;
import et.com.gebeya.user_service.model.Users;
import et.com.gebeya.user_service.repository.RestaurantRepository;
import et.com.gebeya.user_service.repository.UsersRepository;
import et.com.gebeya.user_service.util.MappingUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public String restaurantRegistration(RestaurantRequestDto restaurantRequestDto){
        try{  Restaurant restaurant= MappingUtil.mapRestaurantDtoToModel(restaurantRequestDto);
        restaurant.setStatus(Status.PENDING);
        restaurant.setIsActive(true);
       restaurant=restaurantRepository.save(restaurant);

       Users users=Users.builder()
                .userName(restaurantRequestDto.getUserName())
                .password(passwordEncoder.encode(restaurantRequestDto.getPassword()))
                .role(Role.RESTAURANT)
                .status(Status.PENDING)
                .roleId(restaurant.getId())
               .isActive(true)
                .build();
        usersRepository.save(users);
        return "YOU ARE SUCCESSFULLY REGISTERED, PLEASE WAIT UNTIL THE ADMIN VERIFIES YOUR ACCOUNT";}
      catch(Exception e){
          e.printStackTrace();
      }
      return "failed";
    }
public void approveRestaurant(){
Restaurant restaurant=new Restaurant();
        restaurant.setStatus(Status.VERIFIED);
        Users users=new Users();
        users.setStatus(Status.VERIFIED);
        restaurantRepository.save(restaurant);
        usersRepository.save(users);

}




}
