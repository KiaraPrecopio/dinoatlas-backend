package com.precopio.dinoatlasbackend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Environment {
    LAGOONAL("lagoonal"),
    COASTAL("coastal"),
    INNER_SHELF("inner shelf"),
    OUTER_SHELF("outer shelf"),
    OCEANIC("oceanic"),
    OLIGOTROPHIC("oligotrophic"),
    MESOTROPHIC("mesotrophic"),
    EUTROPHIC("eutrophic"),
    HYPERSALINE("hypersaline"),
    MARINE("marine"),
    BRACKISH("brackish"),
    FRESHWATER("freshwater"),
    TERRESTRIAL("terrestrial"),
    UNKNOWN("unknown");

    private final String value;
}