package com.squad.universityplacement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "students")
public class StudentEntity {

    @Id
    @Column(name = "roll_no", length = 30)
    private String rollNo;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal cgpa;

    @Column(name = "active_backlogs", nullable = false)
    private int activeBacklogs;

    @Enumerated(EnumType.STRING)
    @Column(name = "placement_status", nullable = false, length = 30, columnDefinition = "varchar(30)")
    private PlacementStatus placementStatus;

    protected StudentEntity() {
    }

    public StudentEntity(String rollNo, String name, BigDecimal cgpa,
                         int activeBacklogs, PlacementStatus placementStatus) {
        this.rollNo = rollNo;
        this.name = name;
        this.cgpa = cgpa;
        this.activeBacklogs = activeBacklogs;
        this.placementStatus = placementStatus;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCgpa() {
        return cgpa;
    }

    public int getActiveBacklogs() {
        return activeBacklogs;
    }

    public PlacementStatus getPlacementStatus() {
        return placementStatus;
    }

    public void setPlacementStatus(PlacementStatus placementStatus) {
        this.placementStatus = placementStatus;
    }
}
