package com.precopio.dinoatlasbackend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageType {
    RECONSTRUCTION("reconstruction"),
    FOSSIL("fossil"),
    SKELETON("skeleton"),
    SKULL("skull"),
    HABITAT("habitat"),
    SIZE_COMPARISON("size_comparison"),
    OTHER("other");

    private final String value;
}
