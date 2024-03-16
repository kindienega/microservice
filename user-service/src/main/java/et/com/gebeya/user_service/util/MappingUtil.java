package et.com.gebeya.user_service.util;

import et.com.gebeya.user_service.dto.requestDto.*;
import et.com.gebeya.user_service.dto.responseDto.*;
import et.com.gebeya.user_service.enums.Status;
import et.com.gebeya.user_service.model.*;
import et.com.gebeya.user_service.repository.VendorRepository;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
@AllArgsConstructor
public class MappingUtil {
    private final VendorRepository vendorRepository;

    public static Restaurant mapRestaurantDtoToModel(RestaurantRequestDto dto){
        Restaurant restaurant=new Restaurant();
        restaurant.setBusinessName(dto.getBusinessName());
        restaurant.setOwnerName(dto.getOwnerName());
        restaurant.setLicenseNumber(dto.getLicenseNumber());
        restaurant.setEmail(dto.getEmail());

        List<Address> addresses = null;
        if (dto.getAddresses() != null) {
            addresses = dto.getAddresses()
                    .stream()
                    .map(MappingUtil::mapAddressDtoToModel)
                    .collect(Collectors.toList());
        }
        restaurant.setAddresses(addresses);
        List<PhoneNumber> phoneNumbers = dto.getPhoneNumber()
                .stream()
                .map(MappingUtil::mapPhoneNumberDtoToModel)
                .toList();
        restaurant.setPhoneNumber(phoneNumbers);
        return restaurant;
    }
    public static Vendor mapVendorDtoToModel(VendorRequestDto dto){
        Vendor vendor=new Vendor();
        vendor.setBusinessName(dto.getBusinessName());
        vendor.setOwnerName(dto.getOwnerName());
        vendor.setLicenseNumber(dto.getLicenseNumber());
        vendor.setEmail(dto.getEmail());
        List<Address> addresses=dto.getAddresses()
                .stream()
                .map(MappingUtil::mapAddressDtoToModel).toList();
        vendor.setAddresses(addresses);
        List<PhoneNumber> phoneNumbers = dto.getPhoneNumber()
                .stream()
                .map(MappingUtil::mapPhoneNumberDtoToModel)
                .toList();
        vendor.setPhoneNumber(phoneNumbers);
        return vendor;
    }
    private static PhoneNumber mapPhoneNumberDtoToModel(PhoneNumberDto dto) {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setPhoneNumber(dto.getPhoneNumber());
        phoneNumber.setIsActive(true);
        return phoneNumber;
    }
    private static PhoneNumberResponseDto mapPhoneNumberModelToDto(PhoneNumber phoneNumber) {
        PhoneNumberResponseDto responseDto = new PhoneNumberResponseDto();
        responseDto.setPhoneNumber(phoneNumber.getPhoneNumber());
        return responseDto;
    }
    private static Address mapAddressDtoToModel(AddressRequestDto dto) {
        Address address = new Address();
        address.setCity(dto.getCity());
        address.setSubCity(dto.getSubCity());
        address.setWereda(dto.getWereda());
        address.setIsActive(true);
        return address;
    }

private static AddressResponseDto mapAddressModelToDto(Address address){
    AddressResponseDto responseDto = new AddressResponseDto();
    responseDto.setCity(address.getCity());
    responseDto.setSubCity(address.getSubCity());
    responseDto.setWereda(address.getWereda());
    return responseDto;
}

    public static RestaurantResponseDto mapRestaurantModelToDto(Restaurant restaurant) {
         RestaurantResponseDto responseDto=new RestaurantResponseDto();
         responseDto.setOwnerName(restaurant.getOwnerName());
         responseDto.setBusinessName(responseDto.getBusinessName());

        List<AddressResponseDto> addressResponseDtos=restaurant.getAddresses()
                .stream()
                .map(MappingUtil::mapAddressModelToDto)
                .toList();
        responseDto.setAddresses(addressResponseDtos);
        List<PhoneNumberResponseDto> phoneNumbers = restaurant.getPhoneNumber()
                .stream()
                .map(MappingUtil::mapPhoneNumberModelToDto)
                .toList();
        responseDto.setPhoneNumber(phoneNumbers);
        return responseDto;

    }

    public static AddUserRequest mapCustomerToUser(RestaurantRequestDto dto){
        AddUserRequest userRequest=new AddUserRequest();
        userRequest.setUserName(dto.getUserName());
        userRequest.setPassword(dto.getPassword());
        userRequest.setRole(dto.getRole());
        userRequest.setStatus(dto.getStatus());
        userRequest.setRoleId(dto.getId());
        userRequest.setPhoneNumber(dto.getPhoneNumber().get(0).getPhoneNumber());
        return userRequest;

    }
}
