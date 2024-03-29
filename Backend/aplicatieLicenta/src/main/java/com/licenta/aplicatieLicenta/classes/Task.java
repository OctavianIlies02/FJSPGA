package com.licenta.aplicatieLicenta.classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Job job;

    @Column
    private String taskName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Machine machineRequirement;

    @// TODO: 3/29/2024 lista de perechi energie si timp de procesare

    private List<EnergyProcessingTime> energyProcessingTimeList;
        
    @Column
    private int finishTime;

    public Task() {
    }

    public Task(Job job, String taskName, Machine machineRequirement, int finishTime) {
        this.job = job;
        this.taskName = taskName;
        this.machineRequirement = machineRequirement;
        this.finishTime = finishTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Machine getMachineRequirement() {
        return machineRequirement;
    }

    public void setMachineRequirement(Machine machineRequirement) {
        this.machineRequirement = machineRequirement;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public List<EnergyProcessingTime> getEnergyProcessingTimeList(){
        return energyProcessingTimeList;
    }
}
