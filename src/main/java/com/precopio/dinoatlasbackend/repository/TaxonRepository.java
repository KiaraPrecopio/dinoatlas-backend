package com.precopio.dinoatlasbackend.repository;

import com.precopio.dinoatlasbackend.model.entity.Taxon;
import com.precopio.dinoatlasbackend.model.enums.TaxonomicRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxonRepository extends JpaRepository<Taxon, Long> {

    Optional<Taxon> findByOriginalId(String originalId);

    List<Taxon> findByRank(TaxonomicRank rank);

    List<Taxon> findByRankAndNameContainingIgnoreCase(TaxonomicRank rank, String name);

    @Query("SELECT t FROM Taxon t WHERE t.rank = :rank AND t.isExtant = false")
    List<Taxon> findExtinctByRank(@Param("rank") TaxonomicRank rank);

    @Query("SELECT COUNT(t) FROM Taxon t WHERE t.rank = 'SPECIES'")
    Long countSpecies();

    boolean existsByOriginalId(String originalId);
}