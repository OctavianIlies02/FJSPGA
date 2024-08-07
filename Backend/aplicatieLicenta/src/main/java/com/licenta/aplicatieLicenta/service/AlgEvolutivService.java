package com.licenta.aplicatieLicenta.service;

import com.licenta.aplicatieLicenta.AlgEvolutiv.AlgEvolutiv;
import com.licenta.aplicatieLicenta.AlgEvolutiv.Element;
import com.licenta.aplicatieLicenta.classes.Planification;
import com.licenta.aplicatieLicenta.classes.Job;
import com.licenta.aplicatieLicenta.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class AlgEvolutivService {


    @Autowired
    private JobRepository jobRepository;

    // runs the algorithm and then provides a map of makespan, energy and fitness
    // to be displayed in the frontend
    public Map<String, Object> run(int n, int populationSize, int maxGenerations, double lambda) {
        List<Job> jobList = jobRepository.findAll();
        Planification planification = new Planification(jobList);
        AlgEvolutiv algEvolutiv = new AlgEvolutiv(n, planification, populationSize, maxGenerations, lambda);
        algEvolutiv.runAlgorithm();

        Element element = new Element(n, planification);
        element.evaluateFitness(lambda);


        double makespan = element.getMaxMakespan() + element.getCurrentMakespan() ;
        double energy = element.getCurrentEnergy() +element.getMaxEnergy()  ;
        double fitness = element.getFitness();

        return Map.of(
                "makespan", makespan,
                "energy", energy,
                "fitness", fitness
        );
    }



}

