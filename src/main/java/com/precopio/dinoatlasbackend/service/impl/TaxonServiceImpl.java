package com.precopio.dinoatlasbackend.service.impl;

import com.precopio.dinoatlasbackend.dto.paleobiologydb.TaxonResponseDTO;
import com.precopio.dinoatlasbackend.model.entity.GeologicalInterval;
import com.precopio.dinoatlasbackend.model.entity.Taxon;
import com.precopio.dinoatlasbackend.model.enums.*;
import com.precopio.dinoatlasbackend.repository.TaxonRepository;
import com.precopio.dinoatlasbackend.service.GeologicalIntervalService;
import com.precopio.dinoatlasbackend.service.TaxonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaxonServiceImpl implements TaxonService {

    private final TaxonRepository taxonRepository;
    private final GeologicalIntervalService geologicalIntervalService;

    private final List<String> DINOSAURS_CLASS_NAME = List.of("ornithischia", "saurischia");

    @Transactional
    public void saveOrUpdateTaxonBatch(List<TaxonResponseDTO> dto) {
        List<Taxon> taxons = dto.stream()
                .map(this::mapToEntity)
                .toList();

        taxonRepository.saveAll(taxons);
    }

    private Taxon mapToEntity(TaxonResponseDTO dto) {
        Optional<Taxon> existingTaxon = taxonRepository.findByOriginalId(dto.getTaxonId());

        Taxon taxon;
        if (existingTaxon.isPresent()) {
            taxon = existingTaxon.get();
            log.debug("Updating existing taxon: {}", dto.getTaxonId());
        } else {
            taxon = new Taxon();
            log.debug("Creating new taxon: {}", dto.getTaxonId());
        }

        taxon.setOriginalId(dto.getTaxonId());
        taxon.setName(dto.getScientificName());
        taxon.setRank(mapRank(dto.getRank()));
        taxon.setParentId(dto.getParentTaxonId());
        taxon.setTaxonType(mapTaxonType(dto.getTaxonType()));
        taxon.setIsExtant(mapExtinction(dto.getExtinctFlag()));
        taxon.setFossilsOccurrences(dto.getFossilsOccurrences());


        taxon.setEarliestInterval(mapInterval(dto.getEarliestTimeInterval()));
        taxon.setLatestInterval(mapInterval(
                dto.getLatestTimeInterval() != null
                ? dto.getLatestTimeInterval()
                : dto.getEarliestTimeInterval()
                ));

        taxon.setCommonName((dto.getCommonName() == null || dto.getCommonName().isEmpty())
                ? null : dto.getCommonName());

        taxon.setPhylumName(dto.getPhylumName());
        taxon.setClassName(dto.getClassName());
        taxon.setOrderName(dto.getOrderName());
        taxon.setFamilyName(dto.getFamilyName());
        taxon.setGenusName(dto.getGenusName());
        taxon.setCladeName(dto.getCladeName());

        taxon.setEnvironment(mapEnvironment(dto.getEnvironment()));
        taxon.setLifeHabit(mapLifeHabit(dto.getLifeHabit()));
        taxon.setDiet(mapDiet(dto.getDiet()));

        taxon.setLastSyncedAt(LocalDateTime.now());
        return taxon;
    }

    public Page<TaxonResponseDTO> searchDinosaurs(String name, int page, int size) {
        List<TaxonResponseDTO> filteredTaxons = taxonRepository.findByRankAndClassesNames
                        (TaxonomicRank.SPECIES, DINOSAURS_CLASS_NAME, name, PageRequest.of(page, size))
                .stream()
                .filter(taxon -> taxon.getName().toLowerCase().contains(name.toLowerCase()))
                .map(taxon -> TaxonResponseDTO.builder()
                        .taxonId(taxon.getOriginalId())
                        .scientificName(taxon.getName())
                        .commonName(taxon.getCommonName())
                        .rank(taxon.getRank().name())
                        .parentTaxonId(taxon.getParentId())
                        .taxonType(taxon.getTaxonType().name())
                        .extinctFlag(taxon.getIsExtant() ? "1" : "0")
                        .fossilsOccurrences(taxon.getFossilsOccurrences())
                        .earliestTimeInterval(taxon.getEarliestInterval() != null ? taxon.getEarliestInterval().getName() : null)
                        .latestTimeInterval(taxon.getLatestInterval() != null ? taxon.getLatestInterval().getName() : null)
                        .phylumName(taxon.getPhylumName())
                        .className(taxon.getClassName())
                        .orderName(taxon.getOrderName())
                        .familyName(taxon.getFamilyName())
                        .genusName(taxon.getGenusName())
                        .cladeName(taxon.getCladeName())
                        .environment(taxon.getEnvironment() != null ? taxon.getEnvironment().name() : null)
                        .lifeHabit(taxon.getLifeHabit() != null ? taxon.getLifeHabit().name() : null)
                        .diet(taxon.getDiet() != null ? taxon.getDiet().name() : null)
                        .build())
                .toList();

        return new PageImpl<>(filteredTaxons, PageRequest.of(page, size), filteredTaxons.size());
    }

    public Long getTotalSpeciesCount() {
        return taxonRepository.countSpecies();
    }

    private TaxonType mapTaxonType(String taxonType) {
        if (taxonType == null) return TaxonType.NON_SPECIFIED;
        return switch (taxonType) {
            case "I" -> TaxonType.ICHNO;
            case "F" -> TaxonType.FORM;
            case "IF" -> TaxonType.ICHNO_FORM;
            default -> TaxonType.NON_SPECIFIED;
        };
    }

    private TaxonomicRank mapRank(String rank) {
        if (rank == null) return null;

        try {
            return TaxonomicRank.fromValue(rank.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Unknown taxonomic rank: {}", rank);
            return null;
        }
    }

    private Boolean mapExtinction(String extinctFlag) {
        if (extinctFlag == null) return null;
        return "1".equals(extinctFlag); // 1 = extant (alive), 0 = extinct
    }

    private GeologicalInterval mapInterval(String intervalName) {
        if (intervalName == null || intervalName.isEmpty()) return null;

        return geologicalIntervalService.findIntervalByName(intervalName);
    }

    private Environment mapEnvironment(String environment) {
        if (environment == null) return Environment.UNKNOWN;

        try {
            return Environment.valueOf(environment.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Environment.UNKNOWN;
        }
    }

    private LifeHabit mapLifeHabit(String lifeHabit) {
        if (lifeHabit == null) return LifeHabit.UNKNOWN;

        try {
            return LifeHabit.valueOf(lifeHabit.toUpperCase());
        } catch (IllegalArgumentException e) {
            return LifeHabit.UNKNOWN;
        }
    }

    private DietType mapDiet(String diet) {
        if (diet == null) return DietType.UNKNOWN;

        try {
            return DietType.valueOf(diet.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DietType.UNKNOWN;
        }
    }
}