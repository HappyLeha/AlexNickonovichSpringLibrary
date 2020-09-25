package com.example.demo.service;

import com.example.demo.dto.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.Properties;
@Slf4j
@Service
@Transactional
public class EmailServiceImpl implements EmailService {
    public void sendEmail(EmailDto email) {
        for (String address:email.getEmails()) {
            String from = "alexnickonovichspringlibrary@gmail.com";
            String to = address;
            Properties props;
            props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, "alexnickonovichspringlibrary1#");
                }
            });
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject(email.getSubject());
                message.setText(email.getText());
                Transport.send(message);
                log.info("Email "+email.toString()+" was sent.");
            } catch (MessagingException mex) {
                System.out.println(mex.toString());
            }
        }
    }
}
