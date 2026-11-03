package com.university.placement.service;

import com.university.placement.exception.DuplicateResourceException;
import com.university.placement.exception.ResourceNotFoundException;
import com.university.placement.exception.ValidationException;
import com.university.placement.entity.CompanyEntity;
import com.university.placement.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/** FR-1 (company half) and FR-2: drive registration and the dynamic eligible-drive query. */
@Service
public class CompanyService {

    private static final BigDecimal MAX_CGPA = new BigDecimal("10.00");
    private static final BigDecimal MAX_PACKAGE = new BigDecimal("999.99");

    private final CompanyRepository companyRepository;
    private final StudentService studentService;

    public CompanyService(CompanyRepository companyRepository, StudentService studentService) {
        this.companyRepository = companyRepository;
        this.studentService = studentService;
    }

    /** Validates and registers a visiting company with its eligibility criteria and CTC. */
    @Transactional
    public CompanyEntity registerCompany(String name, BigDecimal minCgpa, int maxBacklogs, BigDecimal packageLpa) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Company name is required.");
        }
        if (name.trim().length() > 150) {
            throw new ValidationException("Company name must be at most 150 characters.");
        }
        if (minCgpa == null || minCgpa.signum() < 0 || minCgpa.compareTo(MAX_CGPA) > 0) {
            throw new ValidationException("Minimum CGPA must be between 0.00 and 10.00.");
        }
        if (minCgpa.scale() > 2) {
            throw new ValidationException("Minimum CGPA can have at most 2 decimal places.");
        }
        if (maxBacklogs < 0) {
            throw new ValidationException("Maximum backlogs cannot be negative.");
        }
        if (packageLpa == null || packageLpa.signum() <= 0 || packageLpa.compareTo(MAX_PACKAGE) > 0) {
            throw new ValidationException("Package must be greater than 0 and at most 999.99 LPA.");
        }
        if (packageLpa.scale() > 2) {
            throw new ValidationException("Package can have at most 2 decimal places.");
        }
        if (companyRepository.existsByNameIgnoreCase(name.trim())) {
            throw new DuplicateResourceException("Company '" + name.trim() + "' is already registered.");
        }
        return companyRepository.save(new CompanyEntity(name.trim(), minCgpa.setScale(2),
                maxBacklogs, packageLpa.setScale(2)));
    }

    @Transactional(readOnly = true)
    public CompanyEntity getCompany(int companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("No company found with id " + companyId + "."));
    }

    @Transactional(readOnly = true)
    public List<CompanyEntity> listCompanies() {
        return companyRepository.findAllByOrderByCompanyIdAsc();
    }

    /**
     * FR-2: companies whose criteria the student meets
     * (student.cgpa &gt;= company.min_cgpa AND student.backlogs &lt;= company.max_backlogs).
     */
    @Transactional(readOnly = true)
    public List<CompanyEntity> findEligibleDrives(String rollNo) {
        String roll = studentService.getStudent(rollNo).getRollNo();
        return companyRepository.findEligibleForStudent(roll);
    }
}
