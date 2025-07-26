package com.precopio.dinoatlasbackend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaxonType {
    REGULAR("regular"),
    ICHNO("ichnotaxon"),
    FORM("form taxon"),
    ICHNO_FORM("ichnofossil form taxon");

    private final String description;
}

