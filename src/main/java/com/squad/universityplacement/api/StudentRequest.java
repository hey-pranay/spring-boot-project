package com.squad.universityplacement.api;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class StudentRequest {

    @NotBlank
    @Size(max = 30)
    private String rollNo;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    @DecimalMin("0.00")
    @DecimalMax("10.00")
    @Digits(integer = 2, fraction = 2)
    private BigDecimal cgpa;

    @Min(0)
    private int activeBacklogs;

    public StudentRequest() {
    }

    public StudentRequest(String rollNo, String name, BigDecimal cgpa, int activeBacklogs) {
        this.rollNo = rollNo;
        this.name = name;
        this.cgpa = cgpa;
        this.activeBacklogs = activeBacklogs;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCgpa() {
        return cgpa;
    }

    public void setCgpa(BigDecimal cgpa) {
        this.cgpa = cgpa;
    }

    public int getActiveBacklogs() {
        return activeBacklogs;
    }

    public void setActiveBacklogs(int activeBacklogs) {
        this.activeBacklogs = activeBacklogs;
    }
}
