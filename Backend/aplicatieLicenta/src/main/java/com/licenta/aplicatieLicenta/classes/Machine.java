package com.licenta.aplicatieLicenta.classes;

import jakarta.persistence.*;

@Entity
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int breakdownTime;
    @Column
    private int mentenance;
    @Column
    private int standardPower;

    public Machine(){}

    public Machine(long id, int breakdownTime, int mentenance, int standardPower){
        this.id = id;
        this.breakdownTime = breakdownTime;
        this.mentenance = mentenance;
        this.standardPower = standardPower;
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

    public int getStandardPower() {
        return standardPower;
    }

    public void setStandardPower(int standardPower) {
        this.standardPower = standardPower;
    }
}