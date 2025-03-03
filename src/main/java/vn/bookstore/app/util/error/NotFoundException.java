package vn.bookstore.app.util.error;

public class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
    }
}
