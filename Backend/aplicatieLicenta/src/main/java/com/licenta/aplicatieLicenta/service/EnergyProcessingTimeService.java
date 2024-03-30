package com.licenta.aplicatieLicenta.service;

import com.licenta.aplicatieLicenta.classes.EnergyProcessingTime;
import com.licenta.aplicatieLicenta.classes.HandlingEquipament;
import com.licenta.aplicatieLicenta.repository.EnergyProcessingTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnergyProcessingTimeService {

    @Autowired
    private EnergyProcessingTimeRepository energyProcessingTimeRepository;

    public EnergyProcessingTime createEnergyProcessingTime(EnergyProcessingTime energyProcessingTime) {
       energyProcessingTimeRepository.save(energyProcessingTime);
        return energyProcessingTime;
    }

    public List<EnergyProcessingTime> getAllEnergyProcessingTimes() {
        return energyProcessingTimeRepository.findAll();
    }

    public Optional<EnergyProcessingTime> getEnergyProcessingTimeById (long id) {
        return energyProcessingTimeRepository.findById(id);
    }
}
