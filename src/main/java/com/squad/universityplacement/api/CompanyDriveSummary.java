package com.squad.universityplacement.api;

import java.math.BigDecimal;

public class CompanyDriveSummary {

    private final String companyName;
    private final BigDecimal packageLpa;
    private final int applicants;
    private final int offers;

    public CompanyDriveSummary(String companyName, BigDecimal packageLpa, int applicants, int offers) {
        this.companyName = companyName;
        this.packageLpa = packageLpa;
        this.applicants = applicants;
        this.offers = offers;
    }

    public String getCompanyName() {
        return companyName;
    }

    public BigDecimal getPackageLpa() {
        return packageLpa;
    }

    public int getApplicants() {
        return applicants;
    }

    public int getOffers() {
        return offers;
    }
}
