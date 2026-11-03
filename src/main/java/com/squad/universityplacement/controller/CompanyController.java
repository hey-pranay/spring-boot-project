package com.squad.universityplacement.controller;

import com.squad.universityplacement.api.CompanyRequest;
import com.squad.universityplacement.entity.CompanyEntity;
import com.squad.universityplacement.service.CompanyService;

import jakarta.validation.Valid;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyEntity registerCompany(@Valid @RequestBody CompanyRequest request) {
        return companyService.registerCompany(request.getName(), request.getMinCgpa(),
                request.getMaxBacklogs(), request.getPackageLpa());
    }

    @GetMapping
    public List<CompanyEntity> listCompanies() {
        return companyService.listCompanies();
    }

    @GetMapping("/eligible")
    public List<CompanyEntity> findEligibleDrives(@RequestParam String rollNo) {
        return companyService.findEligibleDrives(rollNo);
    }

    @GetMapping("/{companyId}")
    public CompanyEntity getCompany(@PathVariable int companyId) {
        return companyService.getCompany(companyId);
    }
}