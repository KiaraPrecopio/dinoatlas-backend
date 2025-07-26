package com.precopio.dinoatlasbackend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DietType {
    CARNIVORE("carnivore"),
    HERBIVORE("herbivore"),
    OMNIVORE("omnivore"),
    PISCIVORE("piscivore"),
    INSECTIVORE("insectivore"),
    UNKNOWN("unknown");

    private final String value;
}
