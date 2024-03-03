package com.licenta.aplicatieLicenta.controllers;

import com.licenta.aplicatieLicenta.classes.HandlingEquipament;
import com.licenta.aplicatieLicenta.classes.Job;
import com.licenta.aplicatieLicenta.service.HandlingEquipamentService;
import com.licenta.aplicatieLicenta.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/handlingequipaments")
public class HandlingEquipamentController {

    @Autowired
    private HandlingEquipamentService handlingEquipamentService;

    @PostMapping
    public ResponseEntity<?> createHandlingEquipament(@RequestBody HandlingEquipament handlingEquipament) {
        try {
            HandlingEquipament createdHandlingEquipament = handlingEquipamentService.createHandlingEquipament(handlingEquipament);
            return new ResponseEntity<>(createdHandlingEquipament, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<HandlingEquipament>> getAllHandlingEquipaments() {
        List<HandlingEquipament> handlingEquipaments = handlingEquipamentService.getAllHendlingEquipaments();
        return new ResponseEntity<>(handlingEquipaments, HttpStatus.OK);
    }
}
