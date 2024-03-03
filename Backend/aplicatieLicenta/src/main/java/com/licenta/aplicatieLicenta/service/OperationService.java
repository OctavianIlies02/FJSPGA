package com.licenta.aplicatieLicenta.service;

import com.licenta.aplicatieLicenta.classes.Job;
import com.licenta.aplicatieLicenta.classes.Operation;
import com.licenta.aplicatieLicenta.repository.JobRepository;
import com.licenta.aplicatieLicenta.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperationService {
    @Autowired
    private OperationRepository operationRepository;

    public Operation createOperation(Operation operation) {
        operationRepository.save(operation);
        return operation;
    }

    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    public Optional<Operation> getOperationById (long id) {
        return operationRepository.findById(id);
    }
}
