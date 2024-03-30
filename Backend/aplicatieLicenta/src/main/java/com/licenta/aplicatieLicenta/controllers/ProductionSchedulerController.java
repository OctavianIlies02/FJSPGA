package com.licenta.aplicatieLicenta.controllers;

import com.licenta.aplicatieLicenta.classes.Job;
import com.licenta.aplicatieLicenta.service.ProductionSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductionSchedulerController {

    @Autowired
    private ProductionSchedulerService productionSchedulerService;

    @PostMapping("/runScheduler")
    public Job runProductionScheduler(@RequestBody InputData inputData) {
        int nJobs = inputData.getNumberOfJobs();
        int nTasks = inputData.getNumberOfTasks();
        double lambda = inputData.getLambda();

        //productionSchedulerService.runProductionScheduler(nJobs, nTasks, lambda);


        return null;
    }

    // Clasa pentru datele de intrare
    static class InputData {
        private int numberOfJobs;
        private int numberOfTasks;
        private double lambda;


        public int getNumberOfJobs() {
            return numberOfJobs;
        }

        public void setNumberOfJobs(int numberOfJobs) {
            this.numberOfJobs = numberOfJobs;
        }

        public int getNumberOfTasks() {
            return numberOfTasks;
        }

        public void setNumberOfTasks(int numberOfTasks) {
            this.numberOfTasks = numberOfTasks;
        }

        public double getLambda() {
            return lambda;
        }

        public void setLambda(double lambda) {
            this.lambda = lambda;
        }
    }
}
