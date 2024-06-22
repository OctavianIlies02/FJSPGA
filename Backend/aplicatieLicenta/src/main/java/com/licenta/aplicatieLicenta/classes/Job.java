package com.licenta.aplicatieLicenta.classes;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "jobs")
@Entity
public class Job {
    @Id
    private int id;
    @Column
    private int arrivalTime;
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    public Job(){}

    public Job(int id, int arrivalTime){
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    public Job(int id, List<Task> tasks){
        this.id = id;
        this.tasks = tasks != null ? tasks : new ArrayList<>();
    }


    @Override
    public String toString() {
        return "Job: id=" + id + ", arrivalTime=" + arrivalTime ;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    //gets the sum of processing time for each task
    public int getTotalLoad() {
        int totalLoad = 0;
        for (Task task : tasks) {
            for (EnergyProcessingTime ept : task.getEnergyProcessingTimeList()) {
                totalLoad += ept.getProcessingTime();
            }
        }
        return totalLoad;
    }

    public int getTaskCount() {
        return tasks.size();
    }

}
