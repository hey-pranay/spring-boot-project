package com.squad.universityplacement.repository;

import java.math.BigDecimal;

public interface OfferAggregateProjection {

    long getTotalOffers();

    BigDecimal getAveragePackageLpa();

    BigDecimal getHighestPackageLpa();
}
