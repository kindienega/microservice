package et.com.gebeya.inventory_management.utility;

import et.com.gebeya.inventory_management.Models.UpdateRequest;
import et.com.gebeya.inventory_management.Models.Vendor;
import et.com.gebeya.inventory_management.dto.VendorDto;
import et.com.gebeya.inventory_management.dto.request.VendorProductUpdateRequestDto;
import et.com.gebeya.inventory_management.dto.response.VendorProductUpdateResponseDto;
import et.com.gebeya.inventory_management.enums.Status;
import et.com.gebeya.inventory_management.exceptions.ResourceNotFoundException;
import et.com.gebeya.inventory_management.repos.VendorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MappingUtil {
    private final VendorRepository vendorRepository;
    public UpdateRequest dtoToEntity(VendorProductUpdateRequestDto dto) {
        Vendor vendor = vendorRepository.findById(dto.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException(" vendor is not found"));
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setName(dto.getNameOfProduct());
        updateRequest.setProductQuantity(dto.getVendorQuantity());
        updateRequest.setVendorProductPrice(dto.getVendorProductPrice());
        updateRequest.setStatus(Status.PENDING);
        updateRequest.setVendor(vendor);
        return updateRequest;
    }

    public VendorProductUpdateResponseDto entityToDto(UpdateRequest updateRequest) {
        VendorProductUpdateResponseDto dto = new VendorProductUpdateResponseDto();
        dto.setNameOfProduct(updateRequest.getName());
        dto.setVendorQuantity(updateRequest.getProductQuantity());
        dto.setVendorProductPrice(updateRequest.getVendorProductPrice());
        dto.setStatus(updateRequest.getStatus());
        dto.setPhoneNumber(updateRequest.getVendor().getPhoneNumber().get(0).getPhoneNumber());
        dto.setVendor(new VendorDto(updateRequest.getVendor().getId(), updateRequest.getVendor().getOwnerName()
                ,updateRequest.getVendor().getLicenseNumber(), updateRequest.getVendor().getPhoneNumber()));
        return dto;
    }
}
