package com.example.demo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.NO_CONTENT)
public class ResourceNoContentException extends Exception {

    public ResourceNoContentException() {
        log.error("ResourceNoContentException");
    }

}
