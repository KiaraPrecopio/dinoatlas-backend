package com.precopio.dinoatlasbackend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GeologicalIntervalType {
    EON("eon"),
    ERA("era"),
    PERIOD("period"),
    EPOCH("epoch"),
    AGE("age");

    private final String value;
}
