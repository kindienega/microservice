package et.com.gebeya.user_service.util;

import et.com.gebeya.user_service.dto.requestDto.*;
import et.com.gebeya.user_service.enums.Role;
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
        return vendor;
    }
    private static PhoneNumber mapPhoneNumberDtoToModel(PhoneNumberDto dto) {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setPhoneNumber(dto.getPhoneNumber());
        phoneNumber.setIsActive(true);
        return phoneNumber;
    }
    private static Address mapAddressDtoToModel(AddressRequestDto dto) {
        Address address = new Address();
        address.setEmail(dto.getEmail());
        address.setCity(dto.getCity());
        address.setSubCity(dto.getSubCity());
        address.setWereda(dto.getWereda());
        address.setIsActive(true);

        List<PhoneNumber> phoneNumbers = dto.getPhoneNumber()
                .stream()
                .map(MappingUtil::mapPhoneNumberDtoToModel)
                .collect(Collectors.toList());
        address.setPhoneNumber(phoneNumbers);

        return address;
    }



}
