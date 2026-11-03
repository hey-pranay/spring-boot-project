package com.squad.universityplacement.service;

import com.squad.universityplacement.api.CompanyDriveSummary;
import com.squad.universityplacement.api.PlacementStats;
import com.squad.universityplacement.entity.InterviewStatus;
import com.squad.universityplacement.entity.PlacementStatus;
import com.squad.universityplacement.repository.ApplicationRepository;
import com.squad.universityplacement.repository.CompanyDriveSummaryProjection;
import com.squad.universityplacement.repository.CompanyRepository;
import com.squad.universityplacement.repository.OfferAggregateProjection;
import com.squad.universityplacement.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class StatisticsService {

    private final StudentRepository studentRepository;
    private final ApplicationRepository applicationRepository;
    private final CompanyRepository companyRepository;

    public StatisticsService(StudentRepository studentRepository,
                             ApplicationRepository applicationRepository,
                             CompanyRepository companyRepository) {
        this.studentRepository = studentRepository;
        this.applicationRepository = applicationRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional(readOnly = true)
    public PlacementStats getPlacementStats() {
        OfferAggregateProjection aggregates = applicationRepository.loadOfferAggregates(InterviewStatus.OFFERED);
        int totalOffers = Math.toIntExact(aggregates.getTotalOffers());
        BigDecimal averagePackage = aggregates.getAveragePackageLpa().setScale(2, RoundingMode.HALF_UP);
        BigDecimal highestPackage = aggregates.getHighestPackageLpa().setScale(2, RoundingMode.HALF_UP);

        return new PlacementStats(Math.toIntExact(studentRepository.count()),
                Math.toIntExact(studentRepository.countByPlacementStatus(PlacementStatus.PLACED)),
                totalOffers, averagePackage, highestPackage);
    }

    @Transactional(readOnly = true)
    public List<CompanyDriveSummary> getDriveSummaries() {
        return companyRepository.findDriveSummaries().stream()
                .map(StatisticsService::toSummary)
                .toList();
    }

    private static CompanyDriveSummary toSummary(CompanyDriveSummaryProjection projection) {
        return new CompanyDriveSummary(projection.getCompanyName(), projection.getPackageLpa(),
                Math.toIntExact(projection.getApplicants()), Math.toIntExact(projection.getOffers()));
    }
}
