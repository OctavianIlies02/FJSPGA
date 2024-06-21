package com.licenta.aplicatieLicenta.AlgEvolutiv;

import com.licenta.aplicatieLicenta.classes.Job;
import com.licenta.aplicatieLicenta.classes.Planification;
import com.licenta.aplicatieLicenta.classes.Task;

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
            if (lambda < 0.6) {
                speed = 1;
            } else if (lambda == 0.6) {
                speed = 2;
            } else if (lambda >= 0.7 && lambda <= 0.9) {
                speed = random.nextInt(2) + 2;
            } else {
                speed = 3;
            }

            Element element = new Element(n, planification);

            for (Job job : element.getPlanification().getJobs()) {
                for (Task task : job.getTasks()) {
                    task.getMachine().setSpeed(speed);
                }
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


    public Element getBestElement() {
        Element bestElement = population.get(0);
        for (Element element : population) {
            if (element.getFitness() < bestElement.getFitness()) {
                bestElement = element;
            }
        }
        return bestElement;
    }



}
