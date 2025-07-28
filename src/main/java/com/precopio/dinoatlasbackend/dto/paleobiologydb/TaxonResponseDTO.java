package com.precopio.dinoatlasbackend.dto.paleobiologydb;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxonResponseDTO {

    @JsonProperty("oid")
    private String taxonId; // e.g. txn:156091

    @JsonProperty("flg")
    private String taxonType;

    @JsonProperty("rnk")
    private String rank; // taxonomic rank (species, genus, etc.)

    @JsonProperty("nam")
    private String scientificName;

    @JsonProperty("nm2")
    private String commonName; // Common name, if available

    @JsonProperty("par")
    private String parentTaxonId;

    @JsonProperty("ext")
    private String extinctFlag; // "0" = extinct, "1" = extant

    @JsonProperty("noc")
    private Integer fossilsOccurrences; // Number of fossil occurrences

    @JsonProperty("tei")
    private String earliestTimeInterval;

    @JsonProperty("tli")
    private String latestTimeInterval;

    // Taxonomic hierarchy
    @JsonProperty("phl")
    private String phylumName;

    @JsonProperty("cll")
    private String className;

    @JsonProperty("odl")
    private String orderName;

    @JsonProperty("fml")
    private String familyName;

    @JsonProperty("gnl")
    private String genusName;

    @JsonProperty("jec")
    private String cladeName;

    // Ecospace attributes
    @JsonProperty("jev")
    private String environment; // e.g. terrestrial, marine

    @JsonProperty("jlh")
    private String lifeHabit; // e.g. boring, cursorial

    @JsonProperty("jdt")
    private String diet; // e.g. herbivore, carnivore, omnivore
}