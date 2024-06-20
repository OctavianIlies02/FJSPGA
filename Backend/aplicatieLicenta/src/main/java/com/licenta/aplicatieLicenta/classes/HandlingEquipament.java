package com.licenta.aplicatieLicenta.classes;

import jakarta.persistence.*;

@Table(name = "handlingEquipament")
@Entity
public class HandlingEquipament {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private int power;
    @Column
    private int speed;

    public HandlingEquipament(){}

    public HandlingEquipament(long id, int power, int speed){
        this.id = id;
        this.power = power;
        this.speed = speed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
