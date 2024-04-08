package com.licenta.aplicatieLicenta.classes;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Table(name = "planifications")
@Entity
public class Planification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Job> jobs;

    @Column
    private int fitness;


    public Planification(){}

    public Planification(Long id, int fintess, Job jobs){
        this.id = id;
        this.fitness = fintess;
        this.jobs = new ArrayList<>();
    }

    public Planification(List<Job> jobs){
        this.jobs = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNuberOfOperations(){
        int nr =0;
        for(Job job : jobs){
            nr += job.getTasks().size();
        }
        return nr;
    }

    public List<Job> getJobs(){
        return jobs;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
}
