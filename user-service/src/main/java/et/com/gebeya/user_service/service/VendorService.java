package et.com.gebeya.user_service.service;

import et.com.gebeya.user_service.dto.requestDto.AddAccountRequestDto;
import et.com.gebeya.user_service.dto.requestDto.RestaurantRequestDto;
import et.com.gebeya.user_service.dto.requestDto.UserCredential;
import et.com.gebeya.user_service.dto.requestDto.VendorRequestDto;
import et.com.gebeya.user_service.enums.Role;
import et.com.gebeya.user_service.enums.Status;
import et.com.gebeya.user_service.exception.ErrorHandler;
import et.com.gebeya.user_service.model.Product;
import et.com.gebeya.user_service.model.Restaurant;
import et.com.gebeya.user_service.model.Users;
import et.com.gebeya.user_service.model.Vendor;
import et.com.gebeya.user_service.repository.ProductRepository;
import et.com.gebeya.user_service.repository.UsersRepository;
import et.com.gebeya.user_service.repository.VendorRepository;
import et.com.gebeya.user_service.repository.specification.RestaurantSpecification;
import et.com.gebeya.user_service.repository.specification.VendorSpecification;
import et.com.gebeya.user_service.util.MappingUtil;
import et.com.gebeya.user_service.util.UserUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class VendorService {
    private final UsersRepository usersRepository;
    private final VendorRepository vendorRepository;
    private final PasswordEncoder passwordEncoder;
    private  final UserUtil userUtil;
    private final ProductRepository productRepository;
    private  Users users;
    @Transactional

    public VendorRequestDto vendorRegistration(VendorRequestDto vendorRequestDto) {
        try {

            Vendor vendor = MappingUtil.mapVendorDtoToModel(vendorRequestDto);
            List<Product> product=getProductById(vendorRequestDto.getProductId());
            vendor.setProducts(product);
            vendor.setIsActive(true);
            vendor = vendorRepository.save(vendor);
            userUtil.createUser(vendor.getBusinessName(), vendor.getOwnerName(), vendor.getId(), Role.VENDOR, Status.APPROVED, vendorRequestDto.getEmail());
//            AddAccountRequestDto addAccountRequestDto=new AddAccountRequestDto();
//            vendorRequestDto.setUserName(addAccountRequestDto.getUsername());
//            vendorRequestDto.setPassword(addAccountRequestDto.getPassword());
            return vendorRequestDto;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new RuntimeException("Failed to register vendor", e);
        }
    }
    private List<Product> getProductById(List<Long> id){
        try{
            return  productRepository.findAllById(id);
        }catch (Exception e){
            throw new ErrorHandler(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
    @Transactional
    public void deleteVendor(Integer vendorId) {
        Optional<Vendor> vendorOptional = vendorRepository.findById(vendorId);
        if (vendorOptional.isPresent()) {
            Vendor vendor = vendorOptional.get();
            vendor.setIsActive(false);
            vendorRepository.save(vendor);
        } else {
            throw new NotFoundException("Vendor not found");
        }
    }

    public List<Vendor> getVendorsByName(String name) {
        Specification<Vendor> spec = VendorSpecification.getVendorByName(name);
        return vendorRepository.findAll(spec);
    }
    public List<Vendor> getAllActiveVendors() {
        Specification<Vendor> spec = VendorSpecification.getAllVendors();
        return vendorRepository.findAll(spec);
    }

    public Vendor getVendorById(Integer id) {
        Specification<Vendor> spec = VendorSpecification.getVendorById(id);
        Optional<Vendor> vendorOptional = vendorRepository.findOne(spec);
        if (vendorOptional.isPresent()) {
            return vendorOptional.get();
        } else {
            throw new NotFoundException("Vendor not found");
        }
    }

}
