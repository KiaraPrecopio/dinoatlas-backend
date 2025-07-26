package com.precopio.dinoatlasbackend.model.entity;

import com.precopio.dinoatlasbackend.model.enums.DietType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "taxon_statistics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxonStatistics {

    @Id
    private Long taxonId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "taxon_id")
    private Taxon taxon;

    @Column(name = "estimated_length_min", precision = 8, scale = 2)
    private BigDecimal estimatedLengthMin; // en metros

    @Column(name = "estimated_length_max", precision = 8, scale = 2)
    private BigDecimal estimatedLengthMax;

    @Column(name = "estimated_weight_min", precision = 10, scale = 2)
    private BigDecimal estimatedWeightMin; // en kg

    @Column(name = "estimated_weight_max", precision = 10, scale = 2)
    private BigDecimal estimatedWeightMax;

    @Column(name = "estimated_height", precision = 8, scale = 2)
    private BigDecimal estimatedHeight;

    @Enumerated(EnumType.STRING)
    @Column(name = "diet_type")
    private DietType dietType;

    @Column(name = "discovery_year")
    private Integer discoveryYear;

    @Column(name = "discovery_country")
    private String discoveryCountry;

    @Column(name = "first_described_by")
    private String firstDescribedBy;

    @Column(name = "total_specimens")
    private Integer totalSpecimens;

    @Column(name = "completeness_percentage", precision = 5, scale = 2)
    private BigDecimal completenessPercentage;

    @Column(name = "calculated_at")
    private LocalDateTime calculatedAt;
}
