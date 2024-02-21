package com.licenta.aplicatieLicenta.classes;

import jakarta.persistence.*;

@Table(name = "jobs")
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private int arrivalTime;
    @Column
    private Machine list;
    @Column
    private int n = 1;
    @Column
    private Operation op;


    public Job(){}

    public Job(long id, String name){
        this.id = id;
        this.name = name;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
