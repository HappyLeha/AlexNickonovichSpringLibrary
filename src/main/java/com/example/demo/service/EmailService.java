package com.example.demo.service;

import com.example.demo.dto.EmailCreateDto;
import javax.mail.MessagingException;

public interface EmailService {

    void sendEmail(EmailCreateDto email) throws MessagingException;

}
