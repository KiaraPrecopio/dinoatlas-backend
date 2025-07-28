package com.precopio.dinoatlasbackend.exception.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotFoundExceptionMessage {

    GEOLOGICAL_INTERVAL_NOT_FOUND("Geological interval [%s] not found.");

    public String format(Object... args) {
        return String.format(value, args);
    }

    private final String value;
}
