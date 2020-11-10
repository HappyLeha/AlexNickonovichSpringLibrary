package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.mail.MessagingException;

@ControllerAdvice
public class EmailNotAvailable {

    @ResponseBody
    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    String emailNotAvailableHandler (MessagingException ex) {
        return ex.getMessage();
    }

}
