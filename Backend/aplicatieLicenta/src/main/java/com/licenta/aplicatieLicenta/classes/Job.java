package com.licenta.aplicatieLicenta.classes;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "jobs")
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int arrivalTime;
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;


    /*@OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
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
    private double fitness; */


    public Job(){}

    public Job(int id, int arrivalTime){
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    public Job(List<Task> tasks){
        this.tasks = new ArrayList<>();

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

}
