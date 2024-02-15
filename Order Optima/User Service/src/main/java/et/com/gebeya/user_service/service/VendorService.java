package et.com.gebeya.user_service.service;

import et.com.gebeya.user_service.dto.requestDto.UserCredential;
import et.com.gebeya.user_service.dto.requestDto.VendorRequestDto;
import et.com.gebeya.user_service.enums.Role;
import et.com.gebeya.user_service.enums.Status;
import et.com.gebeya.user_service.model.Users;
import et.com.gebeya.user_service.model.Vendor;
import et.com.gebeya.user_service.repository.UsersRepository;
import et.com.gebeya.user_service.repository.VendorRepository;
import et.com.gebeya.user_service.util.MappingUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendorService {
    private UsersRepository usersRepository;
    private VendorRepository vendorRepository;

    @Transactional
    public String vendorRegistration(VendorRequestDto vendorRequestDto){
        Vendor vendor= MappingUtil.mapVendorDtoToModel(vendorRequestDto);
        vendorRepository.save(vendor);
        Users users=Users.builder()
                .userName(vendorRequestDto.getUserName())
                .password(vendorRequestDto.getPassword())
                .isActive(true)
                .status(Status.VERIFIED)
                .role(Role.VENDOR)
                .roleId(vendor.getId())
        .build();
usersRepository.save(users);

        return "Vendor is successfully registered";
    }
}
