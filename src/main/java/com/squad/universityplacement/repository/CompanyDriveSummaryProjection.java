package com.squad.universityplacement.repository;

import java.math.BigDecimal;

public interface CompanyDriveSummaryProjection {

    String getCompanyName();

    BigDecimal getPackageLpa();

    long getApplicants();

    long getOffers();
}
