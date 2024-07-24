package et.com.gebeya.inventory_management.payment.controller;

import et.com.gebeya.inventory_management.exceptions.InsufficientBalanceException;
import et.com.gebeya.inventory_management.exceptions.ResourceNotFoundException;
import et.com.gebeya.inventory_management.payment.requestDto.AccountCreationRequestDto;
import et.com.gebeya.inventory_management.payment.requestDto.BalanceDto;
import et.com.gebeya.inventory_management.payment.requestDto.PaymentDto;
import et.com.gebeya.inventory_management.payment.requestDto.PaymentRequestDto;
import et.com.gebeya.inventory_management.payment.responseDto.AccountCreationResponseDto;
import et.com.gebeya.inventory_management.payment.service.MpesaAccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/category/pay")
public class MpesaAccountController {
    private final MpesaAccountService service;

    @PostMapping("/register")
    public ResponseEntity<AccountCreationResponseDto> createAccount(@RequestBody @Valid AccountCreationRequestDto requestDto) throws IOException {
        AccountCreationResponseDto responseDto = service.createAccount(requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AccountCreationResponseDto> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAccount(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/balance/{phoneNumber}")
    public ResponseEntity<BalanceDto> getCurrentBalance(@PathVariable String phoneNumber) {
        BalanceDto balance = service.getCurrentBalance(phoneNumber);
        return ResponseEntity.ok(balance);
    }

    @PutMapping("/balance/{phoneNumber}")
    public ResponseEntity<BalanceDto> updateBalance(@PathVariable String phoneNumber, @RequestBody BalanceDto balanceDto) {
        BalanceDto updatedBalance = service.updateBalance(phoneNumber, balanceDto);
        return ResponseEntity.ok(updatedBalance);
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentDto> processPayment(@RequestBody PaymentRequestDto requestDto) {
        try {
            PaymentDto result = service.processVendorPayment(requestDto);
            return ResponseEntity.ok(result);
        } catch (ResourceNotFoundException | InsufficientBalanceException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
