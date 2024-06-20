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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Planification: [\n");

        for (Job job : planification.getJobs()) {
            stringBuilder.append("\tJob ID: ").append(job.getId()).append("\n");
            List<Task> tasks = job.getTasks();
            for (Task task : tasks) {
                List<EnergyProcessingTime> energyProcessingTimes = task.getEnergyProcessingTimeList();
                stringBuilder.append("\t\t\tEnergy Processing Times: [");
                for (EnergyProcessingTime energyProcessingTime : energyProcessingTimes) {
                    stringBuilder.append("(Time: ").append(energyProcessingTime.getProcessingTime())
                            .append(", Energy: ").append(energyProcessingTime.getEnergy()).append(")");
                    stringBuilder.append(", ");
                }
                if (!energyProcessingTimes.isEmpty()) {
                    stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
                }
                stringBuilder.append("]\n");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }



    public double getFitness() {
        return fitness;
    }

    public List<Integer> getElement() {
        return element;
    }

    public Planification getPlanification(){
        return planification;
    }


    public void evaluateFitness(double lambda) {
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

    public double getMaxMakespan() {
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

    public double getMaxEnergy() {
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


    public void crossover(List<Element> p1, List<Element> p2){
        List<Element> c1 = new ArrayList<>();
        List<Element> c2 = new ArrayList<>();

        List<Integer> selectedList = new ArrayList<>();

        Random random = new Random();
        int r = random.nextInt(p1.size());

        for(int i = 0 ; i < r ; i++){
            int poz_r = random.nextInt(r);
            selectedList.add(poz_r);
        }

        for(int i = 0; i< r ; i++){
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

    public void mutation(List<Element> c){
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


        for (int i = 0; i < sublist.size() - 1; i++) {
            int index1 = random.nextInt(sublist.size());
            int index2 = random.nextInt(sublist.size());
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

    // Job with More Load
    public void applyRuleJML() {
        element.sort((jobId1, jobId2) -> {
            int load1 = getJobLoad(jobId1);
            int load2 = getJobLoad(jobId2);
            return Integer.compare(load2, load1); // sortare descrescatoare dupa load
        });
    }

    // Job with More Tasks
    public void applyRuleJMT() {
        element.sort((jobId1, jobId2) -> {
            int tasksCount1 = getJobTasksCount(jobId1);
            int tasksCount2 = getJobTasksCount(jobId2);
            return Integer.compare(tasksCount2, tasksCount1); // sortare descrescatoare dupa taskuri
        });
    }

    // Shortest Processing Time
    public void applyRuleSPT() {
        element.sort((jobId1, jobId2) -> {
            int config1 = element.get(element.indexOf(jobId1) );
            int config2 = element.get(element.indexOf(jobId2) );
            int processingTime1 = getProcessingTime(jobId1, config1);
            int processingTime2 = getProcessingTime(jobId2, config2);
            return Integer.compare(processingTime1, processingTime2); // sortare crescatoare dupa timpul de procesare
        });
    }

    // Longest Processing Time
    public void applyRuleLPT() {
        element.sort((jobId1, jobId2) -> {
            int config1 = element.get(element.indexOf(jobId1) );
            int config2 = element.get(element.indexOf(jobId2) );
            int processingTime1 = getProcessingTime(jobId1, config1);
            int processingTime2 = getProcessingTime(jobId2, config2);
            return Integer.compare(processingTime2, processingTime1);
        });
    }

    // Machine with More Load
    public void applyRuleMML() {
        element.sort((jobId1, jobId2) -> {
            int load1 = getMachineLoad(jobId1);
            int load2 = getMachineLoad(jobId2);
            return Integer.compare(load2, load1);
        });
    }

    // Machine with More Tasks
    public void applyRuleMMT() {
        element.sort((jobId1, jobId2) -> {
            int tasksCount1 = getMachineTasksCount(jobId1);
            int tasksCount2 = getMachineTasksCount(jobId2);
            return Integer.compare(tasksCount2, tasksCount1); // sortare descrescatoare după numarul de taskuri ale masinii
        });
    }

    public void applyRandomRule() {
        Collections.shuffle(element, new Random());
    }

    private int getJobLoad(int jobId) {
        for (Job job : planification.getJobs()) {
            if (job.getId() == jobId) {
                return job.getTotalLoad();
            }
        }
        return 0;
    }

    private int getJobTasksCount(int jobId) {
        for (Job job : planification.getJobs()) {
            if (job.getId() == jobId) {
                return job.getTaskCount();
            }
        }
        return 0;
    }

    private int getProcessingTime(int jobId, int config) {
        for (Job job : planification.getJobs()) {
            if (job.getId() == jobId) {
                int taskIndex = config / 2;
                if (taskIndex < job.getTasks().size()) {
                    Task task = job.getTasks().get(taskIndex);
                    if (config < task.getEnergyProcessingTimeList().size()) {
                        return task.getEnergyProcessingTimeList().get(config).getProcessingTime();
                    }
                }
            }
        }
        return 0;
    }

    private int getMachineLoad(int jobId) {

        return 0;
    }

    private int getMachineTasksCount(int jobId) {

        return 0;
    }

    public static void main(String args[]) {

       /* List<Job> jobList = createJobList();
        Planification p = new Planification(jobList);
        System.out.println(p); */



      /*  // Creăm lista de joburi
        List<Job> jobList = createJobList();
        // Inițializăm o planificare cu lista de joburi
        Planification planification = new Planification(jobList);
        // Inițializăm un element cu un număr de sarcini și planificarea create
        Element element = new Element(3, planification);

        System.out.println("Element initial:");
        System.out.println(element.toString());

        //pt parinte 2
        List<Job> jobList1 = createParentList();
        Planification planification1 = new Planification(jobList1);
        Element element1 = new Element(3,planification1);

        // Realizăm operația de crossover între elementul inițial și o copie a sa
        Element copyElement = new Element(3, planification);
        List<Element> parent1 = new ArrayList<>();
        List<Element> parent2 = new ArrayList<>();
        parent1.add(element);
        parent2.add(element1);
        element.crossover(parent1, parent2);

        System.out.println("Element dupa crossover:");
        System.out.println(element.toString());

        // Realizăm operația de mutație pe unul dintre părinți
        element.mutation(parent1);

        System.out.println("Element dupa mutatie:");
        System.out.println(element.toString()); */
    }

    /*private static List<Job> createJobList() {
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


        taskList1.add(new Task(energyProcessingTimeList1));// Adăugăm sarcinile cu configurațiile create mai sus
        taskList1.add(new Task(energyProcessingTimeList1));
        taskList1.add(new Task(energyProcessingTimeList1));

        // Creăm sarcinile pentru al doilea job
        List<EnergyProcessingTime> energyProcessingTimeList2 = new ArrayList<>();
        energyProcessingTimeList2.add(new EnergyProcessingTime(1, 1));
        energyProcessingTimeList2.add(new EnergyProcessingTime(3, 3));
        energyProcessingTimeList2.add(new EnergyProcessingTime(1, 1));


        taskList2.add(new Task(energyProcessingTimeList2));
        taskList2.add(new Task(energyProcessingTimeList2));
        taskList2.add(new Task(energyProcessingTimeList2));

        // Creăm sarcinile pentru al treilea job
        List<EnergyProcessingTime> energyProcessingTimeList3 = new ArrayList<>();
        energyProcessingTimeList3.add(new EnergyProcessingTime(2, 2));
        energyProcessingTimeList3.add(new EnergyProcessingTime(1, 1));



        taskList3.add(new Task(energyProcessingTimeList3));
        taskList3.add(new Task(energyProcessingTimeList3));
        taskList3.add(new Task(energyProcessingTimeList3));


        return jobList;
    }

    private static List<Job> createParentList() {
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
        energyProcessingTimeList1.add(new EnergyProcessingTime(3, 3)); // Configurație 1: Timp = 1, Energie = 1
        energyProcessingTimeList1.add(new EnergyProcessingTime(3, 3)); // Configurație 2: Timp = 1, Energie = 1
        energyProcessingTimeList1.add(new EnergyProcessingTime(2, 2)); // Configurație 2: Timp = 1, Energie = 1


        taskList1.add(new Task(energyProcessingTimeList1));// Adăugăm sarcinile cu configurațiile create mai sus
        taskList1.add(new Task(energyProcessingTimeList1));
        taskList1.add(new Task(energyProcessingTimeList1));

        // Creăm sarcinile pentru al doilea job
        List<EnergyProcessingTime> energyProcessingTimeList2 = new ArrayList<>();
        energyProcessingTimeList2.add(new EnergyProcessingTime(2, 2));
        energyProcessingTimeList2.add(new EnergyProcessingTime(3, 3));
        energyProcessingTimeList2.add(new EnergyProcessingTime(1, 1));


        taskList2.add(new Task(energyProcessingTimeList2));
        taskList2.add(new Task(energyProcessingTimeList2));
        taskList2.add(new Task(energyProcessingTimeList2));

        // Creăm sarcinile pentru al treilea job
        List<EnergyProcessingTime> energyProcessingTimeList3 = new ArrayList<>();
        energyProcessingTimeList3.add(new EnergyProcessingTime(1, 1));
        energyProcessingTimeList3.add(new EnergyProcessingTime(2, 2));



        taskList3.add(new Task(energyProcessingTimeList3));
        taskList3.add(new Task(energyProcessingTimeList3));
        taskList3.add(new Task(energyProcessingTimeList3));


        return jobList;
    }*/



}

