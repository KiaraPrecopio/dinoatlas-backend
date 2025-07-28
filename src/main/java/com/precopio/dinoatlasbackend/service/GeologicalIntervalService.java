package com.precopio.dinoatlasbackend.service;

import com.precopio.dinoatlasbackend.dto.paleobiologydb.GeologicalIntervalResponseDTO;
import com.precopio.dinoatlasbackend.model.entity.GeologicalInterval;

public interface GeologicalIntervalService {
    void saveOrUpdateTimeInterval(GeologicalIntervalResponseDTO dto);
    GeologicalInterval findIntervalByName(String name);
}
