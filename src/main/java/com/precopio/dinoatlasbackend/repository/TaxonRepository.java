package com.precopio.dinoatlasbackend.repository;

import com.precopio.dinoatlasbackend.model.entity.Taxon;
import com.precopio.dinoatlasbackend.model.enums.TaxonomicRank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxonRepository extends JpaRepository<Taxon, Long> {

    Optional<Taxon> findByOriginalId(String originalId);

    @Query("SELECT t FROM Taxon t WHERE t.isExtant = false AND "
            + "t.rank = :rank AND "
            + "LOWER(t.className) IN :classesNames AND "
            + "((:name IS NULL OR LOWER(t.commonName) LIKE CONCAT('%', LOWER(:name), '%')) OR "
            + "(LOWER(t.name) LIKE CONCAT('%', LOWER(:name), '%')))")
    Page<Taxon> findByRankAndClassesNames(TaxonomicRank rank, List<String> classesNames, String name, Pageable pageable);

    @Query("SELECT COUNT(t) FROM Taxon t WHERE t.rank = 'SPECIES'")
    Long countSpecies();
}