package com.example.demo.controller;

import com.example.demo.exceptions.PasswordNotEqualableException;
import com.example.demo.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PasswordNotEqualable {

    @ResponseBody
    @ExceptionHandler(PasswordNotEqualableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String passwordNotEqualableHandler (PasswordNotEqualableException ex) {
        return ex.getMessage();
    }

}
