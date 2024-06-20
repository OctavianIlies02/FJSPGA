package com.licenta.aplicatieLicenta.AlgEvolutiv;

import com.licenta.aplicatieLicenta.classes.Planification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AlgEvolutiv {
    private int n; // nr de taskuri
    private Planification planification;
    private List<Element> population;
    private List<Element> tempPopulation;
    private int populationSize;
    private int maxGenerations;
    private double lambda;

    public AlgEvolutiv(int n, Planification planification, int populationSize, int maxGenerations, double lambda) {
        this.n = n;
        this.planification = planification;
        this.populationSize = populationSize;
        this.maxGenerations = maxGenerations;
        this.lambda = lambda;
        this.population = new ArrayList<>();
        this.tempPopulation = new ArrayList<>();
    }

    public void runAlgorithm() {
        int genSize = planification.getJobs().size() * n;
        initializePopulation(genSize);

        evaluateFitness(population);

        int generation = 0;
        while (!stoppingCriterion(generation)) {
            randomShuffle(population);
            for (int i = 0; i < populationSize; i += 2) {
                Element brother = new Element(n, planification);
                Element sister = new Element(n, planification);

                List<Element> parent1 = Collections.singletonList(population.get(i));
                List<Element> parent2 = Collections.singletonList(population.get(i + 1));

                population.get(i).crossover(parent1, parent2);
                population.get(i).mutation(parent1);
                population.get(i + 1).mutation(parent2);

                brother = parent1.get(0);
                sister = parent2.get(0);

                evaluateFitness(Collections.singletonList(brother));
                evaluateFitness(Collections.singletonList(sister));

                saveTempPopulation(tempPopulation, brother, sister);
            }
            updatePopulation();
            generation++;
        }

        reportBestSchedule();
    }

    private void initializePopulation(int genSize) {
        Random random = new Random();
        int speed;

        for (int i = 0; i < populationSize; i++) {
            Element element = new Element(n, planification);

            if (lambda < 0.6) {
                speed = 1;
            } else if (lambda == 0.6) {
                speed = 2;
            } else if (lambda >= 0.7 && lambda <= 0.9) {
                speed = random.nextInt(2) + 2; // 2 sau 3
            } else {
                speed = 3;
            }

            double ruleProbability = random.nextDouble();
            if (ruleProbability < 0.1) {
                element.applyRuleJML(); // Job with More Load
            } else if (ruleProbability < 0.2) {
                element.applyRuleJMT(); // Job with More Tasks
            } else if (ruleProbability < 0.3) {
                element.applyRuleSPT(); // Shortest Processing Time
            } else if (ruleProbability < 0.4) {
                element.applyRuleLPT(); // Longest Processing Time
            } else if (ruleProbability < 0.5) {
                element.applyRuleMML(); // Machine with More Load
            } else if (ruleProbability < 0.6) {
                element.applyRuleMMT(); // Machine with More Tasks
            } else {
                element.applyRandomRule(); // Random Rule
            }


            // atribuire speed?

            population.add(element);
        }
    }


    private void evaluateFitness(List<Element> population) {
        for (Element element : population) {
            element.evaluateFitness(lambda);
        }
    }

    private void randomShuffle(List<Element> population) {
        Collections.shuffle(population);
    }

    private void saveTempPopulation(List<Element> tempPopulation, Element brother, Element sister) {
        tempPopulation.add(brother);
        tempPopulation.add(sister);
    }

    private void updatePopulation() {
        population.clear();
        population.addAll(tempPopulation);
        tempPopulation.clear();
    }

    private boolean stoppingCriterion(int generation) {
        return generation >= maxGenerations;
    }

    private void reportBestSchedule() {
        Element bestElement = population.get(0);
        for (Element element : population) {
            if (element.getFitness() < bestElement.getFitness()) {
                bestElement = element;
            }
        }
        System.out.println("Best schedule: " + bestElement);
    }

   /* public static void main(String[] args) {
        // Example usage
        List<Job> jobList = createJobList();
        Planification planification = new Planification(jobList);
        AlgEvolutiv algEvolutiv = new AlgEvolutiv(3, planification, 10, 100, 0.5);
        algEvolutiv.runAlgorithm();
    }
    private static List<Job> createJobList() {
        List<Job> jobList = new ArrayList<>();
        List<Task> taskList1 = new ArrayList<>();
        List<Task> taskList2 = new ArrayList<>();
        List<Task> taskList3 = new ArrayList<>();
        Job j1 = new Job(1,taskList1);
        Job j2 = new Job(2,taskList2);
        Job j3 = new Job(3,taskList3);
        jobList.add(j1);
        jobList.add(j2);
        jobList.add(j3);

        // Creăm sarcinile pentru primul job
        List<EnergyProcessingTime> energyProcessingTimeList1 = new ArrayList<>();
        energyProcessingTimeList1.add(new EnergyProcessingTime(1, 1)); // Configurație 1: Timp = 1, Energie = 1
        energyProcessingTimeList1.add(new EnergyProcessingTime(3, 3)); // Configurație 2: Timp = 1, Energie = 1
        energyProcessingTimeList1.add(new EnergyProcessingTime(2, 2)); // Configurație 2: Timp = 1, Energie = 1


        taskList1.add(new Task(1,energyProcessingTimeList1));// Adăugăm sarcinile cu configurațiile create mai sus
        taskList1.add(new Task(2,energyProcessingTimeList1));
        taskList1.add(new Task(3,energyProcessingTimeList1));

        // Creăm sarcinile pentru al doilea job
        List<EnergyProcessingTime> energyProcessingTimeList2 = new ArrayList<>();
        energyProcessingTimeList2.add(new EnergyProcessingTime(1, 1));
        energyProcessingTimeList2.add(new EnergyProcessingTime(3, 3));
        energyProcessingTimeList2.add(new EnergyProcessingTime(1, 1));


        taskList2.add(new Task(4,energyProcessingTimeList2));
        taskList2.add(new Task(5,energyProcessingTimeList2));
        taskList2.add(new Task(6,energyProcessingTimeList2));

        // Creăm sarcinile pentru al treilea job
        List<EnergyProcessingTime> energyProcessingTimeList3 = new ArrayList<>();
        energyProcessingTimeList3.add(new EnergyProcessingTime(2, 2));
        energyProcessingTimeList3.add(new EnergyProcessingTime(1, 1));



        taskList3.add(new Task(7,energyProcessingTimeList3));
        taskList3.add(new Task(8,energyProcessingTimeList3));
        taskList3.add(new Task(9,energyProcessingTimeList3));


        return jobList;
    } */
}
