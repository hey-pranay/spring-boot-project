package com.squad.universityplacement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "companies")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Integer companyId;

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column(name = "min_cgpa", nullable = false, precision = 4, scale = 2)
    private BigDecimal minCgpa;

    @Column(name = "max_backlogs", nullable = false)
    private int maxBacklogs;

    @Column(name = "package_lpa", nullable = false, precision = 5, scale = 2)
    private BigDecimal packageLpa;

    protected CompanyEntity() {
    }

    public CompanyEntity(String name, BigDecimal minCgpa, int maxBacklogs, BigDecimal packageLpa) {
        this.name = name;
        this.minCgpa = minCgpa;
        this.maxBacklogs = maxBacklogs;
        this.packageLpa = packageLpa;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getMinCgpa() {
        return minCgpa;
    }

    public int getMaxBacklogs() {
        return maxBacklogs;
    }

    public BigDecimal getPackageLpa() {
        return packageLpa;
    }
}
