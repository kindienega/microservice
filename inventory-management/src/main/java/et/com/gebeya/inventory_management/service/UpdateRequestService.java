package et.com.gebeya.inventory_management.service;

import et.com.gebeya.inventory_management.Models.UpdateRequest;
import et.com.gebeya.inventory_management.dto.request.VendorProductUpdateRequestDto;
import et.com.gebeya.inventory_management.dto.response.VendorProductUpdateResponseDto;
import et.com.gebeya.inventory_management.enums.Status;
import et.com.gebeya.inventory_management.exceptions.ResourceNotFoundException;
import et.com.gebeya.inventory_management.repos.UpdateRequestRepository;
import et.com.gebeya.inventory_management.repos.VendorRepository;
import et.com.gebeya.inventory_management.utility.MappingUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UpdateRequestService {

    private final UpdateRequestRepository updateRequestRepository;
    private final SmsService smsService;
    private final VendorRepository vendorRepository;
    private final MappingUtil mappingUtil;

    @Transactional
    public VendorProductUpdateResponseDto createUpdateRequest(VendorProductUpdateRequestDto dto) {
        UpdateRequest updateRequest = mappingUtil.dtoToEntity(dto);
        updateRequest = updateRequestRepository.save(updateRequest);
        return mappingUtil.entityToDto(updateRequest);
    }

    public List<VendorProductUpdateResponseDto> getAllUpdateRequests() {
        return updateRequestRepository.findAll().stream()
                .map(mappingUtil::entityToDto)
                .collect(Collectors.toList());
    }

    public VendorProductUpdateResponseDto getUpdateRequestById(Long id) {
        UpdateRequest updateRequest = updateRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UpdateRequest not found"));
        return mappingUtil.entityToDto(updateRequest);
    }

    @Transactional
    public VendorProductUpdateResponseDto approveUpdateRequest(Long id) throws IOException {
        UpdateRequest updateRequest = updateRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UpdateRequest not found"));
        updateRequest.setStatus(Status.APPROVED);
        UpdateRequest savedRequest = updateRequestRepository.save(updateRequest);
        String phoneNumber = updateRequest.getVendor().getPhoneNumber().get(0).getPhoneNumber();
        String message = " CONGRATULATIONS ! : Your product update request has been approved.";
        smsService.sendSms(phoneNumber, "e80ad9d8-adf3-463f-80f4-7c4b39f7f164", "", message);

        return mappingUtil.entityToDto(savedRequest);
    }

    @Transactional
    public VendorProductUpdateResponseDto declineUpdateRequest(Long id) throws IOException {
        UpdateRequest updateRequest = updateRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UpdateRequest not found"));
        updateRequest.setStatus(Status.DECLINED);
        UpdateRequest savedRequest = updateRequestRepository.save(updateRequest);
        String phoneNumber = updateRequest.getVendor().getPhoneNumber().get(0).getPhoneNumber();
        String message = " SORRY ! : Your product update request has been declined.";
        smsService.sendSms(phoneNumber, "e80ad9d8-adf3-463f-80f4-7c4b39f7f164", "", message);

        return mappingUtil.entityToDto(savedRequest);
    }

}