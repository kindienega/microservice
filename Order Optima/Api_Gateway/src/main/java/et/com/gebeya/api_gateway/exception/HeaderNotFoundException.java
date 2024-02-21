package et.com.gebeya.api_gateway.exception;

public class HeaderNotFoundException extends RuntimeException{

    public HeaderNotFoundException(String message){
        super(message);
    }
}
