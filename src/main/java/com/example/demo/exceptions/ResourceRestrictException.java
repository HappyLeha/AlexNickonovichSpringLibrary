package com.example.demo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceRestrictException extends Exception {

    public  ResourceRestrictException() {
        log.error("ResourceRestrictException");
    }

}
