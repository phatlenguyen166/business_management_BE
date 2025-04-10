package vn.bookstore.app.util.error;

public class NotFoundValidException extends RuntimeException {
    public NotFoundValidException(String message) {
        super(message);
    }
}
