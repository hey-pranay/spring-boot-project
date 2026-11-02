package com.squad.universityplacement.api;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CompanyRequest {

    @NotBlank
    @Size(max = 150)
    private String name;

    @NotNull
    @DecimalMin("0.00")
    @DecimalMax("10.00")
    @Digits(integer = 2, fraction = 2)
    private BigDecimal minCgpa;

    @Min(0)
    private int maxBacklogs;

    @NotNull
    @DecimalMin("0.01")
    @DecimalMax("999.99")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal packageLpa;

    public CompanyRequest() {
    }

    public CompanyRequest(String name, BigDecimal minCgpa, int maxBacklogs, BigDecimal packageLpa) {
        this.name = name;
        this.minCgpa = minCgpa;
        this.maxBacklogs = maxBacklogs;
        this.packageLpa = packageLpa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMinCgpa() {
        return minCgpa;
    }

    public void setMinCgpa(BigDecimal minCgpa) {
        this.minCgpa = minCgpa;
    }

    public int getMaxBacklogs() {
        return maxBacklogs;
    }

    public void setMaxBacklogs(int maxBacklogs) {
        this.maxBacklogs = maxBacklogs;
    }

    public BigDecimal getPackageLpa() {
        return packageLpa;
    }

    public void setPackageLpa(BigDecimal packageLpa) {
        this.packageLpa = packageLpa;
    }
}
