package com.precopio.dinoatlasbackend.dto.paleobiologydb;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxonListResponseDTO extends ListResponseDTO {

    @JsonProperty("records")
    private List<TaxonResponseDTO> records;
}

