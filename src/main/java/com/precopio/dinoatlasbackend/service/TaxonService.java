package com.precopio.dinoatlasbackend.service;

import com.precopio.dinoatlasbackend.dto.paleobiologydb.TaxonResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaxonService {
    void saveOrUpdateTaxonBatch(List<TaxonResponseDTO> dto);
    Page<TaxonResponseDTO> searchDinosaurs(String name, int page, int size);
    Long getTotalSpeciesCount();
}
