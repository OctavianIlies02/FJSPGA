package com.licenta.aplicatieLicenta.service;

import com.licenta.aplicatieLicenta.classes.HandlingEquipament;
import com.licenta.aplicatieLicenta.repository.HandlingEquipamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HandlingEquipamentService {

    @Autowired
    private HandlingEquipamentRepository handlingEquipamentRepository;

    public HandlingEquipament createHandlingEquipament(HandlingEquipament handlingEquipament) {
        handlingEquipamentRepository.save(handlingEquipament);
        return handlingEquipament;
    }

    public List<HandlingEquipament> getAllHendlingEquipaments() {
        return handlingEquipamentRepository.findAll();
    }

    public Optional<HandlingEquipament> getHandlingEquipamentById (long id) {
        return handlingEquipamentRepository.findById(id);
    }
}
