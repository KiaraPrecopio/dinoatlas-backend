package com.precopio.dinoatlasbackend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Motility {
    ACTIVELY_MOBILE("actively mobile"),
    PASSIVELY_MOBILE("passively mobile"),
    NON_MOBILE("non-mobile"),
    FACULTATIVELY_MOBILE("facultatively mobile"),
    UNKNOWN("unknown");

    private final String value;
}