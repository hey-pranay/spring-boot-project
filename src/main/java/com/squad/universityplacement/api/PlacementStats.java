package com.squad.universityplacement.api;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PlacementStats {

    private final int totalStudents;
    private final int placedStudents;
    private final int totalOffers;
    private final BigDecimal averagePackageLpa;
    private final BigDecimal highestPackageLpa;

    public PlacementStats(int totalStudents, int placedStudents, int totalOffers,
                          BigDecimal averagePackageLpa, BigDecimal highestPackageLpa) {
        this.totalStudents = totalStudents;
        this.placedStudents = placedStudents;
        this.totalOffers = totalOffers;
        this.averagePackageLpa = averagePackageLpa;
        this.highestPackageLpa = highestPackageLpa;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public int getPlacedStudents() {
        return placedStudents;
    }

    public int getTotalOffers() {
        return totalOffers;
    }

    public BigDecimal getAveragePackageLpa() {
        return averagePackageLpa;
    }

    public BigDecimal getHighestPackageLpa() {
        return highestPackageLpa;
    }

    public BigDecimal placementPercentage() {
        if (totalStudents == 0) {
            return BigDecimal.ZERO.setScale(2);
        }
        return BigDecimal.valueOf(placedStudents)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(totalStudents), 2, RoundingMode.HALF_UP);
    }
}
