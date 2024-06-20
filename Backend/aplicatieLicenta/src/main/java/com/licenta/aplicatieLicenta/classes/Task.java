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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Machine machine;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn( name = "task_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<EnergyProcessingTime> energyProcessingTimeList;
        
    @Column
    private int finishTime;

    public Task() {
    }

    public Task(Job job, Machine machine, int finishTime, List<EnergyProcessingTime> energyProcessingTimeList) {
        this.job = job;
        this.machine = machine;
        this.finishTime = finishTime;
        this.energyProcessingTimeList = new ArrayList<>(energyProcessingTimeList);
    }

    public Task(Long id, Job job, Machine machine, List<EnergyProcessingTime> energyProcessingTimeList) {
        this.id = id;
        this.job = job;
        this.machine = machine;
        this.energyProcessingTimeList = new ArrayList<>(energyProcessingTimeList);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Task: id=").append(id).append(", finishTime=").append(finishTime).append("\n");
        stringBuilder.append("Energy Processing Times: \n");
        for (EnergyProcessingTime energyProcessingTime : energyProcessingTimeList) {
            stringBuilder.append("Energy: ").append(energyProcessingTime.getEnergy())
                    .append(", Processing Time: ").append(energyProcessingTime.getProcessingTime()).append("\n");
        }
        return stringBuilder.toString();
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

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machineRequirement) {
        this.machine = machineRequirement;
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
