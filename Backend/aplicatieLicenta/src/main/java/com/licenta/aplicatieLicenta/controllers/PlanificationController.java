package com.licenta.aplicatieLicenta.controllers;

import com.licenta.aplicatieLicenta.classes.Planification;
import com.licenta.aplicatieLicenta.service.PlanificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planifications")
public class PlanificationController {

    @Autowired
    private PlanificationService planificationService ;

    @PostMapping
    public ResponseEntity<?> createPlanification(@RequestBody Planification planification) {
        try {
            Planification createdPlanification = planificationService.createPlanification(planification);
            return new ResponseEntity<>(createdPlanification, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Planification>> getAllPlanifications() {
        List<Planification> planifications = planificationService.getAllPlanifications();
        return new ResponseEntity<>(planifications, HttpStatus.OK);
    }
}
