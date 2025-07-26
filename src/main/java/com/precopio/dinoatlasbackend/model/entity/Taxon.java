package com.precopio.dinoatlasbackend.model.entity;

import com.precopio.dinoatlasbackend.model.enums.Environment;
import com.precopio.dinoatlasbackend.model.enums.Motility;
import com.precopio.dinoatlasbackend.model.enums.TaxonomicRank;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "taxon", indexes = {
        @Index(name = "idx_taxon_oid", columnList = "originalId"),
        @Index(name = "idx_taxon_name", columnList = "name"),
        @Index(name = "idx_taxon_rank", columnList = "rank"),
        @Index(name = "idx_taxon_parent", columnList = "parentId")
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

    @Enumerated(EnumType.STRING)
    @Column(name = "rank", nullable = false)
    private TaxonomicRank rank; // rnk

    @Column(name = "parent_id")
    private String parentId; // par

    @Column(name = "reference_id")
    private String referenceId; // rid

    @Column(name = "flags")
    private String flags; // flg

    @Column(name = "is_extant")
    private Boolean isExtant; // ext (0 = extinct, 1 = extant)

    @Column(name = "occurrence_count")
    private Integer occurrenceCount; // noc

    @Column(name = "size_value")
    private Integer sizeValue; // siz

    @Column(name = "has_exact_synonyms")
    private Integer hasExactSynonyms; // exs

    // Taxonomic hierarchy
    @Column(name = "phylum")
    private String phylum; // phl

    @Column(name = "class_name")
    private String className; // cll

    @Column(name = "order_name")
    private String orderName; // odl

    @Column(name = "family")
    private String family; // fml

    @Column(name = "genus")
    private String genus; // gnl

    // Geological time intervals
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "earliest_interval_id")
    private GeologicalInterval earliestInterval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "latest_interval_id")
    private GeologicalInterval latestInterval;

    @Column(name = "earliest_interval_name")
    private String earliestIntervalName; // tei

    @Column(name = "latest_interval_name")
    private String latestIntervalName; // tli

    // Ecological attributes
    @Enumerated(EnumType.STRING)
    @Column(name = "environment")
    private Environment environment; // jev

    @Column(name = "ecological_category")
    private String ecologicalCategory; // jec

    @Enumerated(EnumType.STRING)
    @Column(name = "motility")
    private Motility motility; // jmo

    @Column(name = "composition")
    private String composition; // jco

    // Relationships
    @OneToMany(mappedBy = "taxon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TaxonOccurrence> occurrences;

    @OneToMany(mappedBy = "taxon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TaxonImage> images;

    @ManyToMany
    @JoinTable(
            name = "taxon_reference",
            joinColumns = @JoinColumn(name = "taxon_id"),
            inverseJoinColumns = @JoinColumn(name = "reference_id")
    )
    private List<Reference> references;

    // Audit fields
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_synced_at")
    private LocalDateTime lastSyncedAt;

    public String getFullTaxonomicName() {
        if (genus != null && name != null) {
            return genus + " " + name.replace(genus + " ", "");
        }
        return name;
    }
}