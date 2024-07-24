package et.com.gebeya.authservice.dto.responsedto;

import et.com.gebeya.authservice.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Builder
@AllArgsConstructor
@Getter
public class ErrorMessageDto {
    private String message;
    private String errorCode; // From your ErrorCode enum
    private int httpStatusCode;
    @Builder.Default
    private String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private String errorId; // Optional, unique identifier for logging
    private Map<String, String> validationErrors; // Optional, map of invalid fields and reasons

    public ErrorMessageDto(String message, ErrorCode errorCode, int httpStatusCode) {
        this.message = message;
        this.errorCode = errorCode.name(); // Use enum name as string
        this.httpStatusCode = httpStatusCode;
    }
}
