package com.licenta.aplicatieLicenta.AlgEvolutiv;

import com.licenta.aplicatieLicenta.classes.EnergyProcessingTime;
import com.licenta.aplicatieLicenta.classes.Job;
import com.licenta.aplicatieLicenta.classes.Planification;
import com.licenta.aplicatieLicenta.classes.Task;

import java.util.*;

public class Element {
    private List<Integer> element = new ArrayList<>();
    private double fitness;
    private Planification planification;
    private int n;

    public Element(int n, Planification planification){
        this.planification = planification;
        this.n = n;
        int nr = 0;
        List<Integer> mylist  = new ArrayList<>();
        List<Integer> auxElement = new ArrayList<>();
        Random random = new Random();
        for(Job job : planification.getJobs()){
            for(Task task : job.getTasks()) {
                nr = task.getEnergyProcessingTimeList();
                auxElement.add(job.getId());
                auxElement.add(random.nextInt(nr));
            }
        }
        for(int i =0; i<n; i++){
            mylist.add(i);
        }
        Collections.shuffle(mylist, new Random());
        for(int i =0; i<n; i++){
            element.add(auxElement.get(i*2));
            element.add(auxElement.get(i*2+1));
        }
    }


    private void evaluateFitness(double lambda) {
        double maxMakespan = getMaxMakespan();
        double maxEnergy = getMaxEnergy();
        double currentMakespan = 0;
        double currentEnergy = 0;
        int jobid;
        int energyMakespanConfiguration;
        int tasks[] = new int[n+1];
        Arrays.fill(tasks,0);

        for(int i = 0; i<n ; i++){
            jobid = element.get(i*2);
            energyMakespanConfiguration = element.get(i*2+1);
            tasks[jobid]++;
            for(Job job : planification.getJobs()){
                if(job.getId() == jobid){
                    for(Task task : job.getTasks()){
                       if (task.getId() == tasks[jobid]){
                           currentMakespan += task.getEnergyProcessingTimeList().get(energyMakespanConfiguration).getProcessingTime();
                           currentEnergy += task.getEnergyProcessingTimeList().get(energyMakespanConfiguration).getEnergy();
                       }
                    }
                    break;
                }
            }
        }
        fitness = (lambda * currentMakespan) + ((1 - lambda) * currentEnergy);

    }

    private double getMaxMakespan() {
        double makespan = 0;
       for(Job job : planification.getJobs()){
           for(Task task : job.getTasks()){
               List<EnergyProcessingTime> list = new ArrayList<>(task.getEnergyProcessingTimeList());
             list.sort((EnergyProcessingTime e1, EnergyProcessingTime e2)-> e1.getEnergy() < e2.getEnergy());
             makespan += list.get(0).getProcessingTime();
           }
       }
       return makespan;
    }

    private double getMaxEnergy() {
        double energy = 0;
        for(Job job : planification.getJobs()){
            for(Task task : job.getTasks()){
                List<EnergyProcessingTime> list = new ArrayList<>(task.getEnergyProcessingTimeList());
                list.sort((EnergyProcessingTime e1, EnergyProcessingTime e2)-> e1.getProcessingTime() < e2.getProcessingTime());
                energy += list.get(0).getEnergy();
            }
        }
        return energy;
    }
}
