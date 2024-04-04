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
                nr = task.getEnergyProcessingTimeList().size();
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
               list.sort((EnergyProcessingTime e1, EnergyProcessingTime e2) -> {
                   if (e1.getEnergy() < e2.getEnergy()) {
                       return -1;
                   } else if (e1.getEnergy() > e2.getEnergy()) {
                       return 1;
                   } else {
                       return 0;
                   }
               });
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
                list.sort((EnergyProcessingTime e1, EnergyProcessingTime e2)-> {
                    if(e1.getProcessingTime() < e2.getProcessingTime()) {
                        return  -1;
                    }else if(e1.getProcessingTime() > e2.getProcessingTime()){
                        return 1;
                    } else {
                        return 0;
                    }
                });
                energy += list.get(0).getEnergy();
            }
        }
        return energy;
    }


    private void crossover(List<Element> p1, List<Element> p2){
        List<Element> c1 = new ArrayList<>();
        List<Element> c2 = new ArrayList<>();

        List<Integer> selectedList = new ArrayList<>();

        Random random = new Random();
        int r = random.nextInt(p1.size());

        for(int i = 0 ; i < r ; i++){
            int poz_r = random.nextInt(r);
            selectedList.add(poz_r);
        }

        for(int i = 0; i< r ; i++){  //se merge prin lista si se adauga elem de pe pozitiile din selectedList din p1 in c1 si in rest se completeaza cu elem din p2
            if(selectedList.contains(i)){
                c1.add(p1.get(i));
            } else{
                c1.add(p2.get(i));
            }
        }

        for(int i= 0; i < r ; i++){
            if(selectedList.contains(i)){
                c2.add(p2.get(i));
            } else{
                c2.add(p2.get(i));
            }
        }

    }

   /* private void mutation(List<Element> c){
        Random random = new Random();
        int a = random.nextInt(c.size());
        int b = random.nextInt(c.size());

        if(a > b){
            int temp = a;
            a = b;
            b = temp;
        }

        List<Element> sublist = c.subList(a,b);
        Collections.shuffle(sublist);


        // Schimbăm aleator valorile de energii și de timp de procesare între elementele din sublist
        for (int i = 0; i < sublist.size() - 1; i++) {
            // Alegem două poziții aleatoare în sublist
            int index1 = random.nextInt(sublist.size());
            int index2 = random.nextInt(sublist.size());
            // Schimbăm valorile de energie și de timp de procesare ale elementelor de pe aceste poziții
            Element element1 = sublist.get(index1);
            Element element2 = sublist.get(index2);
            int tempEnergy = element1.getEnergy();
            int tempProcessingTime = element1.getProcessingTime();
            element1.setEnergy(element2.getEnergy());
            element1.setProcessingTime(element2.getProcessingTime());
            element2.setEnergy(tempEnergy);
            element2.setProcessingTime(tempProcessingTime);
        }
    } */
}
