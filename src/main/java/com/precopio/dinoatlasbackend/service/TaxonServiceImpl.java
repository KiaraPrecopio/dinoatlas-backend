package com.precopio.dinoatlasbackend.service;

import com.precopio.dinoatlasbackend.dto.TaxonResponseDTO;
import com.precopio.dinoatlasbackend.model.entity.Taxon;
import com.precopio.dinoatlasbackend.model.enums.Environment;
import com.precopio.dinoatlasbackend.model.enums.Motility;
import com.precopio.dinoatlasbackend.model.enums.TaxonomicRank;
import com.precopio.dinoatlasbackend.repository.TaxonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaxonServiceImpl implements ITaxonService {

    private final TaxonRepository taxonRepository;

    @Transactional
    public void saveOrUpdateTaxon(TaxonResponseDTO dto) {
        Optional<Taxon> existingTaxon = taxonRepository.findByOriginalId(dto.getTaxonId());

        Taxon taxon;
        if (existingTaxon.isPresent()) {
            taxon = existingTaxon.get();
            log.debug("Updating existing taxon: {}", dto.getTaxonId());
        } else {
            taxon = new Taxon();
            log.debug("Creating new taxon: {}", dto.getTaxonId());
        }

        // Mapear campos básicos
        taxon.setOriginalId(dto.getTaxonId());
        taxon.setName(dto.getScientificName());
        taxon.setRank(mapRank(dto.getRank()));
        taxon.setParentId(dto.getParentTaxonId());
        taxon.setReferenceId(dto.getReferenceId());
        taxon.setFlags(dto.getFlags());

        // Mapear extinción
        taxon.setIsExtant(mapExtinction(dto.getExtinctFlag()));

        // Mapear contadores
        taxon.setOccurrenceCount(dto.getNumberOfChildren());
        taxon.setSizeValue(dto.getSubtreeSize());
        taxon.setHasExactSynonyms(dto.getExtinctionStatus());

        // Mapear jerarquía taxonómica
        taxon.setPhylum(dto.getPhylum());
        taxon.setClassName(dto.getClassName());
        taxon.setOrderName(dto.getOrder());
        taxon.setFamily(dto.getFamily());
        taxon.setGenus(dto.getGenus());

        // Mapear intervalos geológicos
        taxon.setEarliestIntervalName(dto.getEarliestTimeInterval());
        taxon.setLatestIntervalName(dto.getLatestTimeInterval());

        // Mapear atributos ecológicos
        taxon.setEnvironment(mapEnvironment(dto.getEnvironment()));
        taxon.setEcologicalCategory(dto.getClade());
        taxon.setMotility(mapMotility(dto.getMobility()));
        taxon.setComposition(dto.getSkeletalComposition());

        // Actualizar timestamp de sincronización
        taxon.setLastSyncedAt(LocalDateTime.now());

        taxonRepository.save(taxon);
    }

    public List<Taxon> getAllDinosaurs() {
        return taxonRepository.findByRank(TaxonomicRank.SPECIES);
    }

    public List<Taxon> searchDinosaurs(String name) {
        return taxonRepository.findByRankAndNameContainingIgnoreCase(TaxonomicRank.SPECIES, name);
    }

    public Long getTotalSpeciesCount() {
        return taxonRepository.countSpecies();
    }

    // Métodos de mapeo privados
    private TaxonomicRank mapRank(String rank) {
        if (rank == null) return null;

        try {
            return TaxonomicRank.valueOf(rank.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Unknown taxonomic rank: {}", rank);
            return null;
        }
    }

    private Boolean mapExtinction(String extinctFlag) {
        if (extinctFlag == null) return null;
        return !"1".equals(extinctFlag); // 0 = extinct, 1 = extant
    }

    private Environment mapEnvironment(String environment) {
        if (environment == null) return Environment.UNKNOWN;

        try {
            return Environment.valueOf(environment.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Environment.UNKNOWN;
        }
    }

    private Motility mapMotility(String motility) {
        if (motility == null) return Motility.UNKNOWN;

        return switch (motility.toLowerCase()) {
            case "actively mobile" -> Motility.ACTIVELY_MOBILE;
            case "passively mobile" -> Motility.PASSIVELY_MOBILE;
            case "non-mobile" -> Motility.NON_MOBILE;
            case "facultatively mobile" -> Motility.FACULTATIVELY_MOBILE;
            default -> Motility.UNKNOWN;
        };
    }
}