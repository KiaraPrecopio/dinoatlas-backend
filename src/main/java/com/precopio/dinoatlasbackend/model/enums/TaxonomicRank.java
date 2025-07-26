package com.precopio.dinoatlasbackend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaxonomicRank {
    KINGDOM("kingdom"),
    PHYLUM("phylum"),
    CLASS("class"),
    ORDER("order"),
    FAMILY("family"),
    GENUS("genus"),
    SPECIES("species"),
    SUBSPECIES("subspecies");

    private final String value;
}