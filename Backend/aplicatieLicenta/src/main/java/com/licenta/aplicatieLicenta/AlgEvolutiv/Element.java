package com.licenta.aplicatieLicenta.AlgEvolutiv;

import com.licenta.aplicatieLicenta.classes.*;

import java.util.*;

public class Element {
    private List<Integer> element = new ArrayList<>();
    private double fitness;
    private Planification planification;
    private int n;


    // constructs an Element object by initializing it with a random task configuration
    // based on a given planification and a specified number of tasks (n).
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

    // evaluates the fitness of a solution configuration based on the makespan and energy consumption,
    // using a weighted combination controlled by the parameter lambda.
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
        fitness = (lambda * (currentMakespan/getMaxMakespan())) + ((1 - lambda) * (currentEnergy/getMaxEnergy()));

    }
    public double getCurrentMakespan() {
        double currentMakespan = 0;
        int[] tasks = new int[n + 1];
        Arrays.fill(tasks, 0);

        for (int i = 0; i < n; i++) {
            int jobId = element.get(i * 2);
            int energyMakespanConfiguration = element.get(i * 2 + 1);
            tasks[jobId]++;
            for (Job job : planification.getJobs()) {
                if (job.getId() == jobId) {
                    for (Task task : job.getTasks()) {
                        if (task.getId() == tasks[jobId]) {
                            currentMakespan += task.getEnergyProcessingTimeList().get(energyMakespanConfiguration).getProcessingTime();
                        }
                    }
                    break;
                }
            }
        }
        return currentMakespan;
    }

    public double getCurrentEnergy() {
        double currentEnergy = 0;
        int[] tasks = new int[n + 1];
        Arrays.fill(tasks, 0);

        for (int i = 0; i < n; i++) {
            int jobId = element.get(i * 2);
            int energyMakespanConfiguration = element.get(i * 2 + 1);
            tasks[jobId]++;
            for (Job job : planification.getJobs()) {
                if (job.getId() == jobId) {
                    for (Task task : job.getTasks()) {
                        if (task.getId() == tasks[jobId]) {
                            currentEnergy += task.getEnergyProcessingTimeList().get(energyMakespanConfiguration).getEnergy();
                        }
                    }
                    break;
                }
            }
        }
        return currentEnergy;
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




    public void crossover(List<Element> p1, List<Element> p2) {
        List<Element> c1 = new ArrayList<>();
        List<Element> c2 = new ArrayList<>();

        Random random = new Random();
        int r = random.nextInt(p1.size());

        // Select random subset from parent 1 and copy to child 1
        for (int i = 0; i < r; i++) {
            int selectedPosition = random.nextInt(p1.size());
            c1.add(p1.get(selectedPosition));
        }

        // Translate remaining from parent 2 to child 1
        for (int i = 0; i < p2.size(); i++) {
            if (!c1.contains(p2.get(i))) {
                c1.add(p2.get(i));
            }
        }

        // Select random subset from parent 2 and copy to child 2
        for (int i = 0; i < r; i++) {
            int selectedPosition = random.nextInt(p2.size());
            c2.add(p2.get(selectedPosition));
        }

        // Translate remaining from parent 1 to child 2
        for (int i = 0; i < p1.size(); i++) {
            if (!c2.contains(p1.get(i))) {
                c2.add(p1.get(i));
            }
        }
    }

    // mutates a list of elements by shuffling a random sublist of each element's energy processing time list.
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
            return Integer.compare(load2, load1);
        });
    }

    // Job with More Tasks
    public void applyRuleJMT() {
        element.sort((jobId1, jobId2) -> {
            int tasksCount1 = getJobTasksCount(jobId1);
            int tasksCount2 = getJobTasksCount(jobId2);
            return Integer.compare(tasksCount2, tasksCount1);
        });
    }

    // Shortest Processing Time
    public void applyRuleSPT() {
        element.sort((jobId1, jobId2) -> {
            int config1 = element.get(element.indexOf(jobId1) );
            int config2 = element.get(element.indexOf(jobId2) );
            int processingTime1 = getProcessingTime(jobId1, config1);
            int processingTime2 = getProcessingTime(jobId2, config2);
            return Integer.compare(processingTime1, processingTime2);
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
            return Integer.compare(tasksCount2, tasksCount1);
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

    //counts the tasks for each job
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

    // gets the sum of speeds for each task machine
    private int getMachineLoad(int jobId) {
        for (Job job : planification.getJobs()) {
            if (job.getId() == jobId) {
                int totalLoad = 0;
                List<Task> tasks = job.getTasks();
                for (Task task : tasks) {
                    Machine machine = task.getMachine();
                    if (machine != null) {
                        totalLoad += machine.getSpeed() ;
                    }
                }
                return totalLoad;
            }
        }
        return 0;
    }

    //gets the total of tasks for each machine
    private int getMachineTasksCount(int jobId) {
        for (Job job : planification.getJobs()) {
            if (job.getId() == jobId) {
                int totalTasks = 0;
                List<Task> tasks = job.getTasks();
                for (Task task : tasks) {
                    if (task.getMachine() != null) {
                        totalTasks++;
                    }
                }
                return totalTasks;
            }
        }
        return 0;
    }



}

