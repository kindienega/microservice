package et.com.gebeya.user_service.controller;


import et.com.gebeya.user_service.dto.requestDto.VendorProductUpdateRequestDto;
import et.com.gebeya.user_service.dto.responseDto.VendorProductUpdateResponseDto;
import et.com.gebeya.user_service.enums.Status;
import et.com.gebeya.user_service.model.UpdateRequest;
import et.com.gebeya.user_service.service.UpdateRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user/vendor/request")
public class UpdateRequestController {

    private final UpdateRequestService updateRequestService;
    @PostMapping("/request")
    public ResponseEntity<VendorProductUpdateResponseDto> createUpdateRequest(@RequestBody VendorProductUpdateRequestDto requestDto) {
        VendorProductUpdateResponseDto responseDto = updateRequestService.createUpdateRequest(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorProductUpdateResponseDto> getUpdateRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(updateRequestService.getUpdateRequestById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<VendorProductUpdateResponseDto>> getAllUpdateRequests() {
        return ResponseEntity.ok(updateRequestService.getAllUpdateRequests());
    }
    @PutMapping("/{id}/approve")
    public ResponseEntity<VendorProductUpdateResponseDto> approveUpdateRequest(@PathVariable Long id) throws IOException {
        VendorProductUpdateResponseDto responseDto = updateRequestService.approveUpdateRequest(id);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}/decline")
    public ResponseEntity<VendorProductUpdateResponseDto> declineUpdateRequest(@PathVariable Long id) throws IOException {
        VendorProductUpdateResponseDto responseDto = updateRequestService.declineUpdateRequest(id);
        return ResponseEntity.ok(responseDto);
    }

}