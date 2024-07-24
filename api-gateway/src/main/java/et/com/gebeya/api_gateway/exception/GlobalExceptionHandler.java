package et.com.gebeya.api_gateway.exception;


import et.com.gebeya.api_gateway.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HeaderNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleHeaderNotFoundException(HeaderNotFoundException exception)
    {
        ErrorMessageDto errorObject = ErrorMessageDto.builder().message(exception.getMessage()).build();
        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorMessageDto> handleUnAuthorizedException(HeaderNotFoundException exception)
    {
        ErrorMessageDto errorObject = ErrorMessageDto.builder().message(exception.getMessage()).build();
        System.out.println(errorObject);
        return new ResponseEntity<>(errorObject, HttpStatus.UNAUTHORIZED);
    }
}
