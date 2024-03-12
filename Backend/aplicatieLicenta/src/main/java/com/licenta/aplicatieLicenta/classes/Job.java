package com.licenta.aplicatieLicenta.classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Table(name = "jobs")
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int arrivalTime;
    @ElementCollection
    @Column
    private List<String> tasks;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Nullable
    private List<Machine> machines;
    @Column
    private int n = 1;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Nullable
    private Operation operation;
    @Column
    private double fitness;


    public Job(){}

    public Job(long id, int arrivalTime, List<Machine> machines, Operation operation, List<String> tasks, double fitness){
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.machines = machines;
        this.operation = operation;
        this.tasks = tasks;
        this.fitness = fitness;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Nullable
    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(@Nullable List<Machine> machines) {
        this.machines = machines;
    }

    @Nullable
    public Operation getOperation() {
        return operation;
    }

    public void setOperation(@Nullable Operation operation) {
        this.operation = operation;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
