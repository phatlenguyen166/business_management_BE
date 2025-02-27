package vn.bookstore.app.util.error;

public class InvalidDataException extends RuntimeException {
    
    public InvalidDataException(String message) {
        super(message);
    }
    
    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

