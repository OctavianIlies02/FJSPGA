package com.licenta.aplicatieLicenta.classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "energyprocessingtimes")
public class EnergyProcessingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private int energy;

    @Column
    private int processingTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Task task;


    public EnergyProcessingTime(){}

     public EnergyProcessingTime(int energy, int processingTime){
         this.energy = energy;
         this.processingTime = processingTime;
     }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Energy" + energy + "processingTime" + processingTime;
    }


    public int getEnergy(){
         return energy;
     }

     public void setEnergy(int energy){this.energy = energy;}

     public int getProcessingTime(){
         return processingTime;
     }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }


    public void setTask(Task task) {
        this.task = task;
    }
}
