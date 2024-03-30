package com.licenta.aplicatieLicenta.classes;

import jakarta.persistence.*;

@Entity
@Table(name = "energyprocessingtimes")
public class EnergyProcessingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int energy;

    @Column
    private int processingTime;

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


}
