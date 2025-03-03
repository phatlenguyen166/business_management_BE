package vn.bookstore.app.util.error;

public class ExistingIdException extends Exception {
    public ExistingIdException(String message) {
        super(message);
    }
}
