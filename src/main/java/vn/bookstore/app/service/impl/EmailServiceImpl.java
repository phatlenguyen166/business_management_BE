package vn.bookstore.app.service.impl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.bookstore.app.service.EmailService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final SendGrid sendGrid;

    @Override
    public String sendEmail(String toEmail, String subject, String body) {
        Email from = new Email("phatlenguyen166@gmail.com"); // Email của bạn
        Email to = new Email(toEmail);

        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);

            if (response.getStatusCode() == 202) {
                return "Email sent successfully!";
            } else {
                return "Failed to send email: " + response.getBody();
            }

        } catch (IOException e) {
            return "Error occurred while sending email: " + e.getMessage();
        }
    }
}
