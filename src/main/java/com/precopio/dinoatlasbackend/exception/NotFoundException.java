package com.precopio.dinoatlasbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public final String code;

    public NotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }
}
