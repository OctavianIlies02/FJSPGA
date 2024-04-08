package com.licenta.aplicatieLicenta.controllers;

import com.licenta.aplicatieLicenta.classes.Job;
import com.licenta.aplicatieLicenta.classes.Machine;
import com.licenta.aplicatieLicenta.service.JobService;
import com.licenta.aplicatieLicenta.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/machines")
public class MachineController {

    @Autowired
    private MachineService machineService ;

    @PostMapping
    public ResponseEntity<?> createMachine(@RequestBody Machine machine) {
        try {
            Machine createdMachine = machineService.createMachine(machine);
            return new ResponseEntity<>(createdMachine, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Machine>> getAllMachines() {
        List<Machine> machines = machineService.getAllMachines();
        return new ResponseEntity<>(machines, HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getMachineById(@PathVariable("id") long id) {
        try {
            Optional<Machine> machine = machineService.getMachineById(id);
            return new ResponseEntity<>(machine, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
