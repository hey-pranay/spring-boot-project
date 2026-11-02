package com.squad.universityplacement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public PlacementStats getStatistics() {
        return statisticsService.getPlacementStats();
    }

    @GetMapping("/drives")
    public List<CompanyDriveSummary> getDriveSummaries() {
        return statisticsService.getDriveSummaries();
    }
}
