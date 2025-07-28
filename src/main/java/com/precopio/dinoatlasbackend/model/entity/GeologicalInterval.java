package com.precopio.dinoatlasbackend.model.entity;

import com.precopio.dinoatlasbackend.model.enums.GeologicalIntervalType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "geological_interval", indexes = {
        @Index(name = "idx_geological_interval_name", columnList = "name"),
        @Index(name = "idx_geological_interval_interval_type", columnList = "interval_type")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeologicalInterval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "interval_id", unique = true, nullable = false)
    private String intervalId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Enumerated(EnumType.STRING)
    @Column(name = "interval_type", nullable = false)
    private GeologicalIntervalType intervalType; // eon, era, period, epoch, age

    @Column(name = "early_age")
    private Double earlyAge;

    @Column(name = "late_age")
    private Double lateAge;

    @Column(name = "reference_id")
    private String referenceId;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "interval_id")
    private GeologicalInterval parent;

    @OneToMany(mappedBy = "parent")
    private List<GeologicalInterval> children;
}