//package et.com.gebeya.authservice.exceptions;
//
//import et.com.gebeya.authservice.dto.responsedto.ErrorMessageDto;
//import io.jsonwebtoken.ExpiredJwtException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//@ControllerAdvice
//public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<ErrorMessageDto> handleException(ExpiredJwtException e){
//        ErrorMessageDto errorMessageDto=ErrorMessageDto.builder()
//                .message(e.getMessage())
//                .build();
//        return new ResponseEntity<>(errorMessageDto, HttpStatus.UNAUTHORIZED);
//    }
//}
