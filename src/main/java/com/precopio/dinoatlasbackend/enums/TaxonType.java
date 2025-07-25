package com.precopio.dinoatlasbackend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum TaxonType {
    REGULAR("Regular"),
    ICHNO("Ichnotaxon"),
    FORM("Form taxon"),
    ICHNO_FORM("Ichnofossil form taxon");

    private final String description;
}

