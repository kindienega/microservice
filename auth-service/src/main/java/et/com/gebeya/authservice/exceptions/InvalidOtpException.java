package et.com.gebeya.authservice.exceptions;

public class InvalidOtpException  extends RuntimeException{
    public InvalidOtpException(String message){
        super(message);
    }
}

