package com.precopio.dinoatlasbackend.model.entity;

import com.precopio.dinoatlasbackend.model.enums.ImageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "taxon_image")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxonImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taxon_id", nullable = false)
    private Taxon taxon;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "caption")
    private String caption;

    @Column(name = "credit")
    private String credit;

    @Column(name = "license")
    private String license;

    @Enumerated(EnumType.STRING)
    @Column(name = "image_type")
    private ImageType imageType;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;
}
