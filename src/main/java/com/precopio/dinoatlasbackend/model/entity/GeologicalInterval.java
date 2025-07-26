package com.precopio.dinoatlasbackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "geological_interval")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeologicalInterval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "early_age", precision = 10, scale = 2)
    private BigDecimal earlyAge; // En millones de años

    @Column(name = "late_age", precision = 10, scale = 2)
    private BigDecimal lateAge; // En millones de años

    @Column(name = "level") // Era, Period, Epoch, Age
    private String level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_interval_id")
    private GeologicalInterval parentInterval;

    @OneToMany(mappedBy = "parentInterval", cascade = CascadeType.ALL)
    private List<GeologicalInterval> subIntervals;

    // Color para visualización
    @Column(name = "color_hex")
    private String colorHex;
}