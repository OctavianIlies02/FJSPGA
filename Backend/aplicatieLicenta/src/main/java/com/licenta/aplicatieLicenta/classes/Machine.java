package com.licenta.aplicatieLicenta.classes;

import jakarta.persistence.*;

@Table(name = "machines")
@Entity
public class Machine {
    @Id
    private Long id;
    @Column
    private int breakdownTime;
    @Column
    private int mentenance;
    @Column
    private String standardPower;
    @Column
    private int speed;
    @Column
    private int energyConsumption;

    public Machine(){}

    public Machine(long id, int breakdownTime, int mentenance, String standardPower, int speed, int energyConsumption){
        this.id = id;
        this.breakdownTime = breakdownTime;
        this.mentenance = mentenance;
        this.standardPower = standardPower;
        this.speed = speed;
        this.energyConsumption = energyConsumption;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBreakdownTime() {
        return breakdownTime;
    }

    public void setBreakdownTime(int breakdownTime) {
        this.breakdownTime = breakdownTime;
    }

    public int getMentenance() {
        return mentenance;
    }

    public void setMentenance(int mentenance) {
        this.mentenance = mentenance;
    }

    public String getStandardPower() {
        return standardPower;
    }

    public void setStandardPower(String standardPower) {
        this.standardPower = standardPower;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(int energyConsumption) {
        this.energyConsumption = energyConsumption;
    }
}