package com.licenta.aplicatieLicenta.classes;

public class EnergyProcessingTime {
    int energy;
    int processingTime;

     public EnergyProcessingTime(int energy, int processingTime){
         this.energy = energy;
         this.processingTime = processingTime;
     }

     public int getEnergy(){
         return energy;
     }

     public int getProcessingTime(){
         return processingTime;
     }


}
