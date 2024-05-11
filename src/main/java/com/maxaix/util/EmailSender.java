package com.maxaix.util;

import java.io.UnsupportedEncodingException;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Value("${email.from.email}")
    private String emailFromId;

    @Value("${email.from.name}")
    private String emailFromName;

    @Value("${email.user.password}")
    private String emailSmtpPassword;

    @Value("${email.host}")
    private String emailSmtpHost;

    public void sendEmail(String to, String subject, String body) throws MessagingException, UnsupportedEncodingException {
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", emailSmtpHost);

        Session session = Session.getDefaultInstance(props);

        MimeMessage msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(emailFromId, emailFromName));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setSubject(subject);
        msg.setContent(body, "text/html");

        try (Transport transport = session.getTransport()) {
            System.out.println("Sending...");
            transport.connect(emailSmtpHost, emailFromId, emailSmtpPassword);
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }

    }
}
