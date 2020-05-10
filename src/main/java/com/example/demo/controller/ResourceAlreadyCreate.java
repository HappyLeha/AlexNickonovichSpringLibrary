package com.example.demo.controller;

import com.example.demo.exceptions.ResourceAlreadyCreateException;
import com.example.demo.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResourceAlreadyCreate {
    @ResponseBody
    @ExceptionHandler(ResourceAlreadyCreateException.class)

    @ResponseStatus(HttpStatus.CONFLICT)
    String recourceAlreadyCreateHandler (ResourceAlreadyCreateException ex) {
        return ex.getMessage();
    }
}
