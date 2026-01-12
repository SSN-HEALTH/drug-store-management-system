package com.ssnhealthcare.drugstore.scheduler.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${alert.email.recipients:}")
    private String[] recipients;

    public void sendStockAlertEmail(String body) {
        send("Daily Stock Alerts – SSN Healthcare", body);
    }

    public void sendPendingOrderEmail(String body) {
        send("Pending Orders Alert – SSN Healthcare", body);
    }

    private void send(String subject, String body) {

        if (recipients == null || recipients.length == 0) {
            return; // fail-safe
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipients);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
