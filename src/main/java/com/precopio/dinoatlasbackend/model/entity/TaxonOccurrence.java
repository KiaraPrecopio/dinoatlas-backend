package com.precopio.dinoatlasbackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "taxon_occurrence")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxonOccurrence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taxon_id", nullable = false)
    private Taxon taxon;

    @Column(name = "collection_name")
    private String collectionName;

    @Column(name = "formation")
    private String formation;

    @Column(name = "country")
    private String country;

    @Column(name = "state_province")
    private String stateProvince;

    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "geological_interval_id")
    private GeologicalInterval geologicalInterval;

    @Column(name = "min_age", precision = 10, scale = 2)
    private BigDecimal minAge;

    @Column(name = "max_age", precision = 10, scale = 2)
    private BigDecimal maxAge;

    @Column(name = "occurrence_id")
    private String occurrenceId; // ID original de la API

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_id")
    private Reference reference;
}