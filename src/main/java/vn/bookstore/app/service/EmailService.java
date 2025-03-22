package vn.bookstore.app.service;

public interface EmailService {
    String sendEmail(String toEmail, String subject, String body);
}
