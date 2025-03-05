package vn.bookstore.app.util.error;

public class ExistingIdException extends RuntimeException {
    public ExistingIdException(String message) {
        super(message);
    }
}
