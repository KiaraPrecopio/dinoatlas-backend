package com.precopio.dinoatlasbackend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Environment {
    TERRESTRIAL("terrestrial"),
    MARINE("marine"),
    FRESHWATER("freshwater"),
    BRACKISH("brackish"),
    UNKNOWN("unknown");

    private final String value;
}