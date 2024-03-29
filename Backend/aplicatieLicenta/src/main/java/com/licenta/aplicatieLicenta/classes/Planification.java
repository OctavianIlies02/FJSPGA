package com.licenta.aplicatieLicenta.classes;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Table(name = "planifications")
@Entity
public class Planification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Nullable
    private List<Job> jobs;


    private int fitness;




    public Planification(){}

    public Planification(Long id, List<Operation> operations){
        this.id = id;
        this.operations = operations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nullable
    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(@Nullable List<Operation> operations) {
        this.operations = operations;
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
}
