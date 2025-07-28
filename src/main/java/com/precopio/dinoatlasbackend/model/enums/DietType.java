package com.precopio.dinoatlasbackend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DietType {
    CHEMOAUTOTROPH("chemoautotroph"),
    PHOTOAUTOTROPH("photoautotroph"),
    C3_AUTOTROPH("C3 autotroph"),
    C4_AUTOTROPH("C4 autotroph"),
    CAM_AUTOTROPH("CAM autotroph"),
    CHEMOSYMBIOTIC("chemosymbiotic"),
    PHOTOSYMBIOTIC("photosymbiotic"),
    HERBIVORE("herbivore"),
    FRUGIVORE("frugivore"),
    FOLIVORE("folivore"),
    BROWSER("browser"),
    GRAZER("grazer"),
    GRANIVORE("granivore"),
    OMNIVORE("omnivore"),
    INSECTIVORE("insectivore"),
    CARNIVORE("carnivore"),
    MICROCARNIVORE("microcarnivore"),
    PISCIVORE("piscivore"),
    DUROPHAGE("durophage"),
    PARASITE("parasite"),
    SUSPENSION_FEEDER("suspension feeder"),
    OSMOTROPH("osmotroph"),
    DEPOSIT_FEEDER("deposit feeder"),
    DETRITIVORE("detritivore"),
    SAPROPHAGE("saprophage"),
    COPROPHAGE("coprophage"),
    UNKNOWN("unknown");

    private final String value;
}
