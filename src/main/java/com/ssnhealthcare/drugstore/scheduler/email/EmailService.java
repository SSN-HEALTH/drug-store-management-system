package com.ssnhealthcare.drugstore.scheduler.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendStockAlertEmail(List<String> recipients, String body) {
        send("Daily Stock Alerts – SSN Healthcare", recipients, body);
    }

    public void sendPendingOrderEmail(List<String> recipients, String body) {
        send("Pending Orders Alert – SSN Healthcare", recipients, body);
    }

    private void send(String subject, List<String> recipients, String body) {

        if (recipients == null || recipients.isEmpty()) {
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, false, "UTF-8");

            helper.setFrom(from);
            helper.setTo(recipients.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(body, false);

            mailSender.send(message);
            System.out.println("Alert mail sent to " + recipients.size() + " users");

        } catch (Exception e) {
            throw new RuntimeException("Email sending failed", e);
        }
    }
}