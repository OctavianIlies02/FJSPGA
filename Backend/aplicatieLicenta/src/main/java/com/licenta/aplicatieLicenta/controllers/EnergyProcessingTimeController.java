package com.licenta.aplicatieLicenta.controllers;

import com.licenta.aplicatieLicenta.classes.EnergyProcessingTime;
import com.licenta.aplicatieLicenta.classes.Job;
import com.licenta.aplicatieLicenta.classes.Task;
import com.licenta.aplicatieLicenta.service.EnergyProcessingTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/energyprocessingtimes")
public class EnergyProcessingTimeController {

    @Autowired
    private EnergyProcessingTimeService energyProcessingTimeService;

    @PostMapping
    public ResponseEntity<?> createEnergyProcessingTime(@RequestBody EnergyProcessingTime energyProcessingTime) {
        try {
            EnergyProcessingTime createdEnergyProcessingTime = energyProcessingTimeService.createEnergyProcessingTime(energyProcessingTime);
            return new ResponseEntity<>(createdEnergyProcessingTime, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<EnergyProcessingTime>> getAllEnergyProcessingTimes() {
        List<EnergyProcessingTime> energyProcessingTimes = energyProcessingTimeService.getAllEnergyProcessingTimes();
        return new ResponseEntity<>(energyProcessingTimes, HttpStatus.OK);
    }
}
