package com.example.demo.controller;

import com.example.demo.exceptions.ResourceRestrictException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResourceRestrict {
    @ResponseBody
    @ExceptionHandler(ResourceRestrictException.class)

    @ResponseStatus(HttpStatus.CONFLICT)
    String recourceNotFoundHandler (ResourceRestrictException ex) {
        return ex.getMessage();
    }
}
