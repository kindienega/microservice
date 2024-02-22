package et.com.gebeya.user_service.service;

import et.com.gebeya.user_service.dto.requestDto.RestaurantRequestDto;
import et.com.gebeya.user_service.dto.requestDto.UserCredential;
import et.com.gebeya.user_service.dto.requestDto.VendorRequestDto;
import et.com.gebeya.user_service.enums.Role;
import et.com.gebeya.user_service.enums.Status;
import et.com.gebeya.user_service.model.Restaurant;
import et.com.gebeya.user_service.model.Users;
import et.com.gebeya.user_service.model.Vendor;
import et.com.gebeya.user_service.repository.UsersRepository;
import et.com.gebeya.user_service.repository.VendorRepository;
import et.com.gebeya.user_service.util.MappingUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendorService {
    private final UsersRepository usersRepository;
    private final VendorRepository vendorRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public VendorRequestDto vendorRegistration(VendorRequestDto vendorRequestDto){
        Vendor vendor= MappingUtil.mapVendorDtoToModel(vendorRequestDto);
        vendor.setIsActive(true);
        vendor=vendorRepository.save(vendor);

        Users users=Users.builder()
                .userName(vendorRequestDto.getUserName())
                .password(passwordEncoder.encode(vendorRequestDto.getPassword()))
                .role(Role.VENDOR)
                .status(Status.APPROVED)
                .roleId(vendor.getId())
                .isActive(true)
                .build();
        usersRepository.save(users);

        return vendorRequestDto;
    }
}
