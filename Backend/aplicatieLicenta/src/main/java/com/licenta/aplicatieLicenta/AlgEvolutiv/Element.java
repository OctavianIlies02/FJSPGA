package com.licenta.aplicatieLicenta.AlgEvolutiv;

import com.licenta.aplicatieLicenta.classes.*;

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

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Element: [");
        for (int i = 0; i < element.size(); i += 2) {
            int jobId = element.get(i);
            int energyMakespanConfiguration = element.get(i + 1);
            stringBuilder.append("(Job ID: ").append(jobId).append(", Energy-Makespan Configuration: ").append(energyMakespanConfiguration).append(")");
            if (i < element.size() - 2) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }


    public Planification getPlanification(){
        return planification;
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

    private void mutation(List<Element> c){
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
            int tempEnergy = element1.getPlanification().getJobs().get(i).getTasks().get(i).getEnergyProcessingTimeList().get(i).getEnergy();
            int tempProcessingTime = element1.getPlanification().getJobs().get(i).getTasks().get(i).getEnergyProcessingTimeList().get(i).getProcessingTime();
            element1.getPlanification().getJobs().get(i).getTasks().get(i).getEnergyProcessingTimeList().get(i).setEnergy(element2.getPlanification().getJobs().get(i).getTasks().get(i).getEnergyProcessingTimeList().get(i).getEnergy());
            element1.getPlanification().getJobs().get(i).getTasks().get(i).getEnergyProcessingTimeList().get(i).setProcessingTime(element2.getPlanification().getJobs().get(i).getTasks().get(i).getEnergyProcessingTimeList().get(i).getProcessingTime());
            element2.getPlanification().getJobs().get(i).getTasks().get(i).getEnergyProcessingTimeList().get(i).setEnergy(tempEnergy);
            element2.getPlanification().getJobs().get(i).getTasks().get(i).getEnergyProcessingTimeList().get(i).setProcessingTime(tempProcessingTime);
        }
    }

    public static void main(String args[]) {

        List<Job> jobList1 = new ArrayList<>();
        List<Task> taskList1 = new ArrayList<>();
        List<Task> taskList2 = new ArrayList<>();
        List<Task> taskList3 = new ArrayList<>();
        List<EnergyProcessingTime> energyProcessingTimeList = new ArrayList<>();

        EnergyProcessingTime ept1 = new EnergyProcessingTime(1,1);
        EnergyProcessingTime ept2 = new EnergyProcessingTime(1,1);
        EnergyProcessingTime ept3 = new EnergyProcessingTime(1,1);
        EnergyProcessingTime ept4 = new EnergyProcessingTime(1,1);
        EnergyProcessingTime ept5 = new EnergyProcessingTime(1,1);
        EnergyProcessingTime ept6 = new EnergyProcessingTime(1,1);
        EnergyProcessingTime ept7 = new EnergyProcessingTime(1,1);
        EnergyProcessingTime ept8 = new EnergyProcessingTime(1,1);

        energyProcessingTimeList.add(ept1);
        energyProcessingTimeList.add(ept2);
        energyProcessingTimeList.add(ept3);
        energyProcessingTimeList.add(ept4);
        energyProcessingTimeList.add(ept5);
        energyProcessingTimeList.add(ept6);
        energyProcessingTimeList.add(ept7);
        energyProcessingTimeList.add(ept8);

        Task t1 = new Task(energyProcessingTimeList);
        Task t2 = new Task(energyProcessingTimeList);
        Task t3 = new Task(energyProcessingTimeList);
        Task t4 = new Task(energyProcessingTimeList);
        Task t5 = new Task(energyProcessingTimeList);
        Task t6 = new Task(energyProcessingTimeList);
        Task t7 = new Task(energyProcessingTimeList);
        Task t8 = new Task(energyProcessingTimeList);

        taskList1.add(t1);
        taskList1.add(t2);
        taskList1.add(t3);
        taskList2.add(t4);
        taskList2.add(t5);
        taskList2.add(t6);
        taskList3.add(t7);
        taskList3.add(t8);

        Job j1 = new Job(taskList1);
        Job j2 = new Job(taskList2);
        Job j3 = new Job(taskList3);

        jobList1.add(j1);
        jobList1.add(j2);
        jobList1.add(j3);

        Planification p1 = new Planification(jobList1);






        // Creare obiect de tip Element cu numărul specificat și planificarea p1
        Element e1 = new Element(3, p1);

        // Creare liste de elemente pentru crossover
        List<Element> s1 = new ArrayList<>();
        List<Element> s2 = new ArrayList<>();
        // Adăugare e1 în ambele liste de elemente
        s1.add(e1);
        s2.add(e1);

        // Afisarea listei s1 inainte de crossover
        System.out.println("s1 inainte de crossover:");
        for (Element element : s1) {
            System.out.println(element.toString());
        }

        // Apelarea metodei crossover pe e1
        e1.crossover(s1, s2);

        // Afisarea listei s1 după crossover
        System.out.println("s1 dupa crossover:");
        for (Element element : s1) {
            System.out.println(element.toString());
        }
    }


}

