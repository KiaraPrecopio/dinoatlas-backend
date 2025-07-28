package com.precopio.dinoatlasbackend.dto.paleobiologydb;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListResponseDTO {

    @JsonProperty("elapsed_time")
    private Double elapsedTime;

    @JsonProperty("warnings")
    private List<String> warnings;
}
