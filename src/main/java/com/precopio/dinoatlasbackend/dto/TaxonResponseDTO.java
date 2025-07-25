package com.precopio.dinoatlasbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxonResponseDTO {

    @JsonProperty("oid")
    private String taxonId; // e.g. txn:156091

    @JsonProperty("flg")
    private String flags; // e.g. "I", "F", "IF"

    @JsonProperty("rnk")
    private String rank; // taxonomic rank (species, genus, etc.)

    @JsonProperty("nam")
    private String scientificName;

    @JsonProperty("acc")
    private String acceptedTaxonId;

    @JsonProperty("acr")
    private Integer acceptedRankCode;

    @JsonProperty("acn")
    private String acceptedName;

    @JsonProperty("par")
    private String parentTaxonId;

    @JsonProperty("rid")
    private String referenceId;

    @JsonProperty("ext")
    private String extinctFlag;

    @JsonProperty("noc")
    private Integer numberOfChildren;

    @JsonProperty("tei")
    private String earliestTimeInterval;

    @JsonProperty("tli")
    private String latestTimeInterval;

    @JsonProperty("siz")
    private Integer subtreeSize;

    @JsonProperty("exs")
    private Integer extinctionStatus;

    @JsonProperty("phl")
    private String phylum;

    @JsonProperty("cll")
    private String className;

    @JsonProperty("odl")
    private String order;

    @JsonProperty("fml")
    private String family;

    @JsonProperty("gnl")
    private String genus;

    @JsonProperty("jev")
    private String environment; // e.g. terrestrial

    @JsonProperty("jec")
    private String clade; // e.g. Diapsida

    @JsonProperty("jmo")
    private String mobility; // e.g. actively mobile

    @JsonProperty("jco")
    private String skeletalComposition; // e.g. hydroxyapatite
}
