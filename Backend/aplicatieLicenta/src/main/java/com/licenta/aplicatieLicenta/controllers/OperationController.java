package com.licenta.aplicatieLicenta.controllers;

import com.licenta.aplicatieLicenta.classes.Machine;
import com.licenta.aplicatieLicenta.classes.Operation;
import com.licenta.aplicatieLicenta.service.MachineService;
import com.licenta.aplicatieLicenta.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
public class OperationController {

    @Autowired
    private OperationService operationService ;

    @PostMapping
    public ResponseEntity<?> createOperation(@RequestBody Operation operation) {
        try {
            Operation createdOperation = operationService.createOperation(operation);
            return new ResponseEntity<>(createdOperation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Operation>> getAllOperations() {
        List<Operation> operations = operationService.getAllOperations();
        return new ResponseEntity<>(operations, HttpStatus.OK);
    }
}
