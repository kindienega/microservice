package et.com.gebeya.user_service.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serial;


@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor

public class ErrorHandler extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    private HttpStatus status;
    private String message;

    public ErrorHandler(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

}
