package com.licenta.aplicatieLicenta.service;

import com.licenta.aplicatieLicenta.classes.Job;
import com.licenta.aplicatieLicenta.classes.Planification;
import com.licenta.aplicatieLicenta.repository.JobRepository;
import com.licenta.aplicatieLicenta.repository.PlanificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanificationService {
    @Autowired
    private PlanificationRepository planificationRepository;

    public Planification createPlanification(Planification planification) {
        planificationRepository.save(planification);
        return planification;
    }

    public List<Planification> getAllPlanifications() {
        return planificationRepository.findAll();
    }

    public Optional<Planification> getPlanificationById (long id) {
        return planificationRepository.findById(id);
    }
}
