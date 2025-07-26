package com.precopio.dinoatlasbackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "reference")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_id", unique = true)
    private String originalId; // ref:xxxxx from API

    @Column(name = "title", length = 1000)
    private String title;

    @Column(name = "authors", length = 1000)
    private String authors;

    @Column(name = "publication_year")
    private Integer publicationYear;

    @Column(name = "journal")
    private String journal;

    @Column(name = "volume")
    private String volume;

    @Column(name = "pages")
    private String pages;

    @Column(name = "doi")
    private String doi;

    @Column(name = "publication_type")
    private String publicationType;

    @ManyToMany(mappedBy = "references")
    private List<Taxon> taxa;

    @OneToMany(mappedBy = "reference")
    private List<TaxonOccurrence> occurrences;
}
