package com.precopio.dinoatlasbackend.service;

import com.precopio.dinoatlasbackend.dto.TaxonResponseDTO;
import com.precopio.dinoatlasbackend.model.entity.Taxon;

import java.util.List;

public interface ITaxonService {
    void saveOrUpdateTaxon(TaxonResponseDTO dto);
    List<Taxon> getAllDinosaurs();
    List<Taxon> searchDinosaurs(String name);
    Long getTotalSpeciesCount();
}
