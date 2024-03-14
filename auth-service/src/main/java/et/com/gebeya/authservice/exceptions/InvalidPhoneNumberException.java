package et.com.gebeya.authservice.exceptions;

public class InvalidPhoneNumberException extends RuntimeException{
    public InvalidPhoneNumberException(String message){
        super(message);
    }
}
