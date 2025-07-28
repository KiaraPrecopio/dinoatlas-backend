package com.precopio.dinoatlasbackend.model.entity;

import com.precopio.dinoatlasbackend.model.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "taxon", indexes = {
        @Index(name = "idx_taxon_name", columnList = "name"),
        @Index(name = "idx_taxon_common_name", columnList = "commonName"),
        @Index(name = "idx_taxon_rank", columnList = "rank"),
        @Index(name = "idx_taxon_is_extant", columnList = "isExtant"),
        @Index(name = "idx_taxon_diet", columnList = "diet"),
        @Index(name = "idx_taxon_class_name", columnList = "className"),
        @Index(name = "idx_taxon_order_name", columnList = "orderName"),
        @Index(name = "idx_taxon_clade_name", columnList = "cladeName")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Taxon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_id", unique = true, nullable = false)
    private String originalId; // oid from API

    @Column(name = "name", nullable = false)
    private String name; // nam

    @Column(name = "common_name")
    private String commonName; // nm2

    @Enumerated(EnumType.STRING)
    @Column(name = "rank", nullable = false)
    private TaxonomicRank rank; // rnk

    @Column(name = "parent_id")
    private String parentId; // par

    @Enumerated(EnumType.STRING)
    @Column(name = "taxon_type", nullable = false)
    private TaxonType taxonType;

    @Column(name = "is_extant")
    private Boolean isExtant; // ext (0 = extinct, 1 = extant)

    @Column(name = "fossils_occurrences")
    private Integer fossilsOccurrences; // noc

    // Geological time intervals
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "earliest_interval_id")
    private GeologicalInterval earliestInterval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "latest_interval_id")
    private GeologicalInterval latestInterval;

    // Taxonomic hierarchy
    @Column(name = "phylum_name")
    private String phylumName; // phl

    @Column(name = "class_name")
    private String className; // cll

    @Column(name = "order_name")
    private String orderName; // odl

    @Column(name = "family_name")
    private String familyName; // fml

    @Column(name = "genus_name")
    private String genusName; // gnl

    @Column(name = "clade_name")
    private String cladeName; // jec

    // Ecospace attributes
    @Enumerated(EnumType.STRING)
    @Column(name = "environment")
    private Environment environment; // jev

    @Enumerated(EnumType.STRING)
    @Column(name = "life_habit")
    private LifeHabit lifeHabit; // jlh

    @Enumerated(EnumType.STRING)
    @Column(name = "diet")
    private DietType diet; // jdt

    // Audit fields
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_synced_at")
    private LocalDateTime lastSyncedAt;
}