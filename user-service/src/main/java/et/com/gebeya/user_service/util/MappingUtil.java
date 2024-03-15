package et.com.gebeya.user_service.util;

import et.com.gebeya.user_service.dto.requestDto.*;
import et.com.gebeya.user_service.dto.responseDto.AddressResponseDto;
import et.com.gebeya.user_service.dto.responseDto.PhoneNumberResponseDto;
import et.com.gebeya.user_service.dto.responseDto.RestaurantResponseDto;
import et.com.gebeya.user_service.dto.responseDto.VendorResponse;
import et.com.gebeya.user_service.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class MappingUtil {
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

    public VendorResponse transformToVendorResponse(Vendor vendor) {
        VendorResponse response = new VendorResponse();
        // Assuming you have setters or a constructor to set these fields
        response.setId(vendor.getId());
        response.setBusinessName(vendor.getBusinessName());
        response.setOwnerName(vendor.getOwnerName());
        response.setLicenseNumber(vendor.getLicenseNumber());
        response.setEmail(vendor.getEmail());
        response.setAddresses(vendor.getAddresses().stream().map(this::transformAddress).collect(Collectors.toList()));
        response.setPhoneNumber(vendor.getPhoneNumber().stream().map(this::transformPhoneNumber).collect(Collectors.toList()));
        response.setProductId(vendor.getProducts().stream().map(this::transformProductId).collect(Collectors.toList()));

        return response;
    }

    private PhoneNumberDto transformPhoneNumber(PhoneNumber phoneNumber) {
        PhoneNumberDto phoneNumbers = new PhoneNumberDto();
        phoneNumbers.setPhoneNumber(phoneNumber.getPhoneNumber());
        return phoneNumbers;
    }
    private ProductId transformProductId(Product products){
        ProductId productId = new ProductId();
        productId.setProductId(products.getId());
        return productId;
    }

    private AddressRequestDto transformAddress(Address address) {
        AddressRequestDto response = new AddressRequestDto();
        response.setCity(address.getCity());
        response.setSubCity(address.getSubCity());
        response.setWereda(address.getWereda());
        return response;
    }
    public VendorProductUpdateRequestDto mapToEntity(Product product ){
        VendorProductUpdateRequestDto vpUpdate = new VendorProductUpdateRequestDto();
        vpUpdate.setNameOfProduct(product.getName());
       // vpUpdate.setVendorQuantity(product.getVendorQuantity());
       // vpUpdate.setVendorProductPrice(product.getVendorProductPrice());
        return vpUpdate;
    }
    public UpdateRequest mapDtoToEntity(VendorProductUpdateRequestDto dto) {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setName(dto.getNameOfProduct());
        updateRequest.setProductQuantity(dto.getVendorQuantity());
        updateRequest.setVendorProductPrice(dto.getVendorProductPrice());
        updateRequest.setStatus(dto.getStatus());
        return updateRequest;
    }

    public VendorProductUpdateRequestDto mapEntityToDto(UpdateRequest entity) {
        VendorProductUpdateRequestDto dto = new VendorProductUpdateRequestDto();
        dto.setNameOfProduct(entity.getName());
        dto.setVendorQuantity(entity.getProductQuantity());
        dto.setVendorProductPrice(entity.getVendorProductPrice());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
