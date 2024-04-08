package com.licenta.aplicatieLicenta.controllers;

import com.licenta.aplicatieLicenta.classes.EnergyProcessingTime;
import com.licenta.aplicatieLicenta.classes.Job;
import com.licenta.aplicatieLicenta.service.EnergyProcessingTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/energyprocessingtimes")
public class EnergyProcessingTimeController {

    @Autowired
    private EnergyProcessingTimeService energyProcessingTimeService;

    @GetMapping
    public ResponseEntity<List<EnergyProcessingTime>> getAllEnergyProcessingTimes() {
        List<EnergyProcessingTime> energyProcessingTimes = energyProcessingTimeService.getAllEnergyProcessingTimes();
        return new ResponseEntity<>(energyProcessingTimes, HttpStatus.OK);
    }
}
