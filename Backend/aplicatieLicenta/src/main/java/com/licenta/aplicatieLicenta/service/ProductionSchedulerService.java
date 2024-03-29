package com.licenta.aplicatieLicenta.service;

import com.licenta.aplicatieLicenta.classes.Job;
import com.licenta.aplicatieLicenta.classes.Machine;
import com.licenta.aplicatieLicenta.classes.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductionSchedulerService {

    private double lambda;

    @Autowired
    private JobService jobService;

    @Autowired
    private TaskService taskService;


    public void runProductionScheduler(int nJobs, int nTasks, double lambda) {
        int genSize = nJobs * nTasks;
        List<Job> population = new ArrayList<>();
        List<Job> tempPopulation = new ArrayList<>();
        int currentIteration = 0;
        int maxIterations = 3;

        if (lambda < 0.6) {
            initialPopulation(genSize, 1, population);
        } else if (lambda == 0.6) {
            initialPopulation(genSize, 2, population);
        } else if (lambda > 0.6 && lambda <= 0.9) {
            initialPopulation(genSize, getRandomSpeed(2, 3), population);
        } else if (lambda > 0.9) {
            initialPopulation(genSize, 3, population);
        }

        evaluateFitness(population);


        while (!stoppingCriterionFulfilled(currentIteration, maxIterations)) {
            randomShuffle(population);

            for (int i = 0; i < population.size(); i += 2) {
                Job brother = population.get(i);
                Job sister = population.get(i + 1);

                crossover(brother, sister);
                mutation(brother, sister);

                evaluateFitness(brother, sister);

                saveTempPopulation(brother, sister,tempPopulation);
            }

            updatePopulation(population, tempPopulation);
            currentIteration++;

        }

        Job bestSchedule = findBestSchedule(population);
        System.out.println("Best Schedule: " + bestSchedule);
    }

    private void randomShuffle(List<Job> population) {
        Collections.shuffle(population);
    }

    private void initialPopulation(int genSize, int speed, List<Job> population) {
        List<Job> jobs = jobService.getAllJobs();
        List<Task> tasks = taskService.getAllTasks(); // Obținem toate sarcinile din baza de date

        // Sortează job-urile în funcție de reguli de dispatching
        sortJobsByDispatchingRule(jobs, DispatchingRule.JML);

        for (int i = 0; i < genSize; i++) {
            Job job = jobs.get(i % jobs.size());
            job.setTasks(tasks); // Setează lista de sarcini pentru fiecare job
            population.add(job);
        }

        for (Job schedule : population) {
            for (Machine machine : schedule.getMachines()) {
                machine.setSpeed(speed);
            }
        }
    }

    private void sortJobsByDispatchingRule(List<Job> jobs, DispatchingRule rule) {
        switch (rule) {
            case JML:
                jobs.sort(Comparator.comparingInt(job -> -job.getTasks().size())); // Sortare descrescătoare după numărul de sarcini
                break;
            case JMT:
                jobs.sort(Comparator.comparingInt(job -> -job.getTasks().size())); // Sortare descrescătoare după numărul de sarcini
                break;
            case SPT:
                jobs.sort(Comparator.comparingInt(job -> -job.getArrivalTime())); // Sortare descrescătoare după timpul de sosire
                break;
            case LPT:
                jobs.sort(Comparator.comparingInt(job -> job.getArrivalTime())); // Sortare crescătoare după timpul de sosire
                break;
            case MML:
                jobs.sort(Comparator.comparingInt(job -> -job.getMachines().size())); // Sortare descrescătoare după numărul de mașini
                break;
            case MMT:
                jobs.sort(Comparator.comparingInt(job -> -job.getMachines().size())); // Sortare descrescătoare după numărul de mașini
                break;
        }
    }

    enum DispatchingRule {
        JML,
        JMT,
        SPT,
        LPT,
        MML,
        MMT
    }

    private int getRandomSpeed(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }



    private void crossover(Job brother, Job sister) {
        int chromosomeLength = brother.getMachines().size(); // Lungimea cromozomului

        // Selectează un subset aleatoriu de sarcini (job, energy) din primul părinte
        int subsetStart = getRandomIndex(chromosomeLength - 1); // Scade 1 pentru a evita generarea unui index egal cu lungimea cromozomului
        int subsetEnd = getRandomIndex(chromosomeLength - 1); // Scade 1 pentru a evita generarea unui index egal cu lungimea cromozomului

        // Asigură că subsetEnd este mai mare decât subsetStart
        if (subsetEnd < subsetStart) {
            int temp = subsetEnd;
            subsetEnd = subsetStart;
            subsetStart = temp;
        }

        // Copiază subsetul din primul părinte în aceeași poziție în descendent (frate)
        for (int i = subsetStart; i <= subsetEnd; i++) {
            brother.getMachines().set(i, sister.getMachines().get(i));
        }

        // Traduce perechile rămase (job, energy) din al doilea părinte în aceeași ordine în descendentă (soră)
        for (int i = 0; i < chromosomeLength; i++) {
            if (i < subsetStart || i > subsetEnd) {
                sister.getMachines().set(i, brother.getMachines().get(i));
            }
        }
    }



    private int getRandomIndex(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }




    private void mutation(Job brother, Job sister) {
        double mutationProbability = 0.1; // Setează probabilitatea de mutație (10% în acest exemplu)
        int chromosomeLength = brother.getMachines().size() ; // Lungimea cromozomului

        // Verifică dacă se aplică mutația pentru frate
        if (Math.random() < mutationProbability) {
            // Selectează două perechi (task, energy) aleatorii în cadrul cromozomului
            int positionA = getRandomIndex(chromosomeLength);
            int positionB = getRandomIndex(chromosomeLength);

            // Asigură că positionB este mai mare decât positionA
            if (positionB < positionA) {
                int temp = positionB;
                positionB = positionA;
                positionA = temp;
            }

            // Mutația: amestecă perechile alese aleatoriu în cromozom
            for (int i = positionA; i <= positionB; i++) {
                Collections.swap(brother.getMachines(), i, getRandomIndex(chromosomeLength));
            }

            // Mutația: modifică aleatoriu vitezele mașinilor pentru fiecare task
            for (int i = 0; i < chromosomeLength; i++) {
                brother.getMachines().get(i).setSpeed(getRandomSpeed(1, 3));
            }
        }

        // Verifică dacă se aplică mutația pentru soră (același procedeu)
        if (Math.random() < mutationProbability) {
            int positionA = getRandomIndex(chromosomeLength);
            int positionB = getRandomIndex(chromosomeLength);

            if (positionB < positionA) {
                int temp = positionB;
                positionB = positionA;
                positionA = temp;
            }

            for (int i = positionA; i <= positionB; i++) {
                Collections.swap(sister.getMachines(), i, getRandomIndex(chromosomeLength));
            }

            for (int i = 0; i < chromosomeLength; i++) {
                sister.getMachines().get(i).setSpeed(getRandomSpeed(1, 3));
            }
        }
    }


    private void saveTempPopulation(Job brother, Job sister, List<Job> tempPopulation) {
        tempPopulation.add(brother);
        tempPopulation.add(sister);
    }


    private void updatePopulation(List<Job> population, List<Job> tempPopulation) {
        population.clear();
        population.addAll(tempPopulation);
    }

    private Job findBestSchedule(List<Job> population) {
        return Collections.max(population, Comparator.comparing(Job::getArrivalTime));
    }

    private boolean stoppingCriterionFulfilled(int currentIteration, int maxIterations) {
        return currentIteration >= maxIterations;
    }

}
