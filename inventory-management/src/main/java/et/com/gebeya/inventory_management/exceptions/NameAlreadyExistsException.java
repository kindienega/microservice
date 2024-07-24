package et.com.gebeya.inventory_management.exceptions;

public class NameAlreadyExistsException extends RuntimeException {

    public NameAlreadyExistsException(String name) {
        super("The name '" + name + "' already exists.");
    }
}