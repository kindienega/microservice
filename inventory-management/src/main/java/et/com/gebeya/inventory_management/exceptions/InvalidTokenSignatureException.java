package et.com.gebeya.inventory_management.exceptions;

public class InvalidTokenSignatureException extends RuntimeException {
    public InvalidTokenSignatureException(String message) {
        super(message);
    }
}
