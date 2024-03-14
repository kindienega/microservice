package et.com.gebeya.authservice.exceptions;

import et.com.gebeya.authservice.dto.responsedto.ErrorMessageDto;
import et.com.gebeya.authservice.enums.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.UUID;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDto> handleGlobalException(Exception e){
        logger.error("[Global Exception]: {}");
        log.error(e.getMessage(),e);
        ErrorCode errorCode=ErrorCode.GENERAL_ERROR;
        int httpStatusCode=HttpStatus.INTERNAL_SERVER_ERROR.value();

        if (e instanceof InvalidPhoneNumberException){
            errorCode=ErrorCode.INVALID_EMAIL;
            httpStatusCode=HttpStatus.BAD_REQUEST.value();
        } else if (e instanceof ExpiredJwtException) {
            errorCode = ErrorCode.TOKEN_EXPIRED;
            httpStatusCode = HttpStatus.UNAUTHORIZED.value();

        }
        if (e instanceof  PasswordChangeException){
            errorCode=ErrorCode.RESOURCE_NOT_FOUND;
            httpStatusCode=HttpStatus.NOT_FOUND.value();
        }

        ErrorMessageDto errorMessageDto = ErrorMessageDto.builder()
                .message(e.getMessage())
                .errorCode(String.valueOf(errorCode))
                .httpStatusCode(httpStatusCode)
                .errorId(UUID.randomUUID().toString())
                .build();

        return new ResponseEntity<>(errorMessageDto, HttpStatus.valueOf(httpStatusCode));



}}
