package com.precopio.dinoatlasbackend.service.impl;

import com.precopio.dinoatlasbackend.dto.paleobiologydb.GeologicalIntervalResponseDTO;
import com.precopio.dinoatlasbackend.exception.NotFoundException;
import com.precopio.dinoatlasbackend.exception.messages.NotFoundExceptionMessage;
import com.precopio.dinoatlasbackend.model.entity.GeologicalInterval;
import com.precopio.dinoatlasbackend.model.enums.GeologicalIntervalType;
import com.precopio.dinoatlasbackend.repository.GeologicalIntervalRepository;
import com.precopio.dinoatlasbackend.service.GeologicalIntervalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeologicalIntervalServiceImpl implements GeologicalIntervalService {

    private final GeologicalIntervalRepository geologicalIntervalRepository;

    @Override
    public void saveOrUpdateTimeInterval(GeologicalIntervalResponseDTO dto) {
        log.debug("Saving or updating geological interval: {}", dto.getName());

        GeologicalInterval interval = geologicalIntervalRepository.findByNameIgnoreCase(dto.getName())
                .orElse(new GeologicalInterval());

        interval.setIntervalId(dto.getIntervalId());
        interval.setName(dto.getName());
        interval.setAbbreviation(dto.getAbbreviation());
        interval.setIntervalType(mapIntervalType(dto.getIntervalType()));
        interval.setParent(mapParentInterval(dto.getParentId()));
        interval.setEarlyAge(dto.getEarlyAge());
        interval.setLateAge(dto.getLateAge());
        interval.setReferenceId(dto.getReferenceId());

        geologicalIntervalRepository.save(interval);
        log.info("Geological interval saved/updated successfully: {}", dto.getName());
    }

    @Override
    public GeologicalInterval findIntervalByName(String name) {
        log.info("Searching for geological interval with name: {}", name);
        return geologicalIntervalRepository.findByNameIgnoreCase(name)
                .orElse(null);
    }

    // Private methods

    private GeologicalIntervalType mapIntervalType(String intervalType) {
        if (intervalType == null || intervalType.isEmpty()) return null;

        try {
            return GeologicalIntervalType.valueOf(intervalType.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Unknown geological interval type: {}", intervalType);
            return null;
        }
    }

    private GeologicalInterval mapParentInterval(String parentId) {
        if (parentId == null || parentId.isEmpty()) return null;

        return geologicalIntervalRepository.findByIntervalId(parentId)
                .orElseThrow(() -> new NotFoundException(
                        NotFoundExceptionMessage.GEOLOGICAL_INTERVAL_NOT_FOUND.format(parentId),
                        NotFoundExceptionMessage.GEOLOGICAL_INTERVAL_NOT_FOUND.name()
                ));
    }
}
