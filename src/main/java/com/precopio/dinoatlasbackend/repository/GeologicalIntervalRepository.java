package com.precopio.dinoatlasbackend.repository;

import com.precopio.dinoatlasbackend.model.entity.GeologicalInterval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeologicalIntervalRepository extends JpaRepository<GeologicalInterval, Integer> {

    Optional<GeologicalInterval> findByNameIgnoreCase(String name);

    Optional<GeologicalInterval> findByIntervalId(String intervalId);
}
