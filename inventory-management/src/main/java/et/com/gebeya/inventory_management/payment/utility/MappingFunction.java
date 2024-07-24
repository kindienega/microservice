package et.com.gebeya.inventory_management.payment.utility;

import et.com.gebeya.inventory_management.exceptions.PhoneNumberAlreadyExistsException;
import et.com.gebeya.inventory_management.payment.model.MpesaAccount;
import et.com.gebeya.inventory_management.payment.repository.MpesaAccountRepository;
import et.com.gebeya.inventory_management.payment.requestDto.AccountCreationRequestDto;
import et.com.gebeya.inventory_management.payment.responseDto.AccountCreationResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MappingFunction {
    private final MpesaAccountRepository mpesaAccountRepository;

    public MpesaAccount dtoToEntity(AccountCreationRequestDto dto) {
        if (mpesaAccountRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExistsException("An account with phone number " + dto.getPhoneNumber() + " already exists.");
        }
        MpesaAccount account = new MpesaAccount();
        account.setFirstName(dto.getFirstName());
        account.setLastName(dto.getLastName());
        account.setPhoneNumber(dto.getPhoneNumber());
        account.setBalance(dto.getBalance());
        account.setCity(dto.getCity());
        return account;
    }

    public AccountCreationResponseDto entityToDto(MpesaAccount account) {
        AccountCreationResponseDto dto = new AccountCreationResponseDto();
        dto.setId(account.getId());
        dto.setFirstName(account.getFirstName());
        dto.setLastName(account.getLastName());
        dto.setPhoneNumber(account.getPhoneNumber());
        dto.setBalance(account.getBalance());
        dto.setCity(account.getCity());
        return dto;
    }
}
