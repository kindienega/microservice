package et.com.gebeya.user_service.util;

import et.com.gebeya.user_service.dto.requestDto.*;
import et.com.gebeya.user_service.dto.responseDto.AddressResponseDto;
import et.com.gebeya.user_service.dto.responseDto.PhoneNumberResponseDto;
import et.com.gebeya.user_service.dto.responseDto.RestaurantResponseDto;
import et.com.gebeya.user_service.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class MappingUtil {
    public static Restaurant mapRestaurantDtoToModel(RestaurantRequestDto dto){
        Restaurant restaurant=new Restaurant();
        restaurant.setBusinessName(dto.getBusinessName());
        restaurant.setOwnerName(dto.getOwnerName());
        restaurant.setLicenseNumber(dto.getLicenseNumber());
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
        address.setEmail(dto.getEmail());
        address.setCity(dto.getCity());
        address.setSubCity(dto.getSubCity());
        address.setWereda(dto.getWereda());
        address.setIsActive(true);
        return address;
    }

private static AddressResponseDto mapAddressModelToDto(Address address){
    AddressResponseDto responseDto = new AddressResponseDto();
    responseDto.setEmail(address.getEmail());
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
}
