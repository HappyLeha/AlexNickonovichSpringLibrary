package com.example.demo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class PasswordNotEqualableException extends Exception {

    public PasswordNotEqualableException() {
        log.error("PasswordNotEqualableException");
    }

}
