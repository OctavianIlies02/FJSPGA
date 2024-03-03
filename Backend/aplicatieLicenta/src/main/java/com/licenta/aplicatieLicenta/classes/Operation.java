package com.licenta.aplicatieLicenta.classes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Table(name = "operations")
@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int toolChangingTime;
    @Column
    private int toolSettingTime;
    @Column
    private int clampingTime;
    @Column
    private int endingTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Nullable
    private Machine machine;

    public Operation(){}

    public Operation(Long id, int toolChangingTime, int toolSettingTime, int clampingTime, int endingTime, Machine machine){
        this.id = id;
        this.toolChangingTime = toolChangingTime;
        this.toolSettingTime = toolSettingTime;
        this.clampingTime = clampingTime;
        this.endingTime = endingTime;
        this.machine = machine;

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public int getToolChangingTime() {
        return toolChangingTime;
    }

    public void setToolChangingTime(int toolChangingTime) {
        this.toolChangingTime = toolChangingTime;
    }

    public int getToolSettingTime() {
        return toolSettingTime;
    }

    public void setToolSettingTime(int toolSettingTime) {
        this.toolSettingTime = toolSettingTime;
    }

    public int getClampingTime() {
        return clampingTime;
    }

    public void setClampingTime(int clampingTime) {
        this.clampingTime = clampingTime;
    }

    public int getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(int endingTime) {
        this.endingTime = endingTime;
    }

    @Nullable
    public Machine getMachine() {
        return machine;
    }

    public void setMachine(@Nullable Machine machine) {
        this.machine = machine;
    }
}
