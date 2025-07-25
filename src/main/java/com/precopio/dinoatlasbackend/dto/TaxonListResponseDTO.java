package com.precopio.dinoatlasbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TaxonListResponseDTO {
    @JsonProperty("records")
    private List<TaxonResponseDTO> records;
}

