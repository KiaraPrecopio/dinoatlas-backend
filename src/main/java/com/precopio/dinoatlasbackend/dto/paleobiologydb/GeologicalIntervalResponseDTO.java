package com.precopio.dinoatlasbackend.dto.paleobiologydb;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeologicalIntervalResponseDTO {

    @JsonProperty("oid")
    private String intervalId;

    @JsonProperty("nam")
    private String name;

    @JsonProperty("abr")
    private String abbreviation;

    @JsonProperty("itp")
    private String intervalType; // era, period, epoch, age

    @JsonProperty("pid")
    private String parentId;

    @JsonProperty("eag")
    private Double earlyAge;

    @JsonProperty("lag")
    private Double lateAge;

    @JsonProperty("rid")
    private String referenceId;
}
