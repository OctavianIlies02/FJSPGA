package com.licenta.aplicatieLicenta.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Machine machineRequirement;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn( name = "task_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<EnergyProcessingTime> energyProcessingTimeList;
        
    @Column
    private int finishTime;

    public Task() {
    }

    public Task(Job job, Machine machineRequirement, int finishTime) {
        this.job = job;
        this.machineRequirement = machineRequirement;
        this.finishTime = finishTime;
        this.energyProcessingTimeList = new ArrayList<>();
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

    public void setEnergyProcessingTimeList(List<EnergyProcessingTime> energyProcessingTimeList) {
        this.energyProcessingTimeList = energyProcessingTimeList;
    }

}
