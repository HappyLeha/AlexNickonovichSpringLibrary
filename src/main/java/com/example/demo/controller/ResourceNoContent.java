package com.example.demo.controller;

import com.example.demo.exceptions.ResourceNoContentException;
import com.example.demo.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResourceNoContent {
    @ResponseBody
    @ExceptionHandler(ResourceNoContentException.class)

    @ResponseStatus(HttpStatus.NO_CONTENT)
    String recourceNoContentHandler (ResourceNoContentException ex) {
        return ex.getMessage();
    }
}
