package com.squad.universityplacement.service;

import com.squad.universityplacement.api.ApplicationDetail;
import com.squad.universityplacement.api.ApplicationResponse;
import com.squad.universityplacement.api.StatusUpdateResponse;
import com.squad.universityplacement.entity.ApplicationEntity;
import com.squad.universityplacement.entity.CompanyEntity;
import com.squad.universityplacement.entity.InterviewStatus;
import com.squad.universityplacement.entity.PlacementStatus;
import com.squad.universityplacement.entity.StudentEntity;
import com.squad.universityplacement.exception.DuplicateResourceException;
import com.squad.universityplacement.exception.EligibilityException;
import com.squad.universityplacement.exception.InvalidStatusTransitionException;
import com.squad.universityplacement.exception.ResourceNotFoundException;
import com.squad.universityplacement.exception.ValidationException;
import com.squad.universityplacement.repository.ApplicationRepository;
import com.squad.universityplacement.repository.CompanyRepository;
import com.squad.universityplacement.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              StudentRepository studentRepository,
                              CompanyRepository companyRepository) {
        this.applicationRepository = applicationRepository;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public ApplicationResponse applyForDrive(String rollNo, int companyId) {
        String roll = StudentService.normalizeRollNo(rollNo);
        StudentEntity student = studentRepository.findById(roll)
                .orElseThrow(() -> new ResourceNotFoundException("No student found with roll number " + roll + "."));
        CompanyEntity company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("No company found with id " + companyId + "."));

        if (student.getPlacementStatus() != PlacementStatus.UNPLACED) {
            throw new EligibilityException("Student " + roll + " is " + student.getPlacementStatus()
                    + " and cannot apply to new drives.");
        }
        if (student.getCgpa().compareTo(company.getMinCgpa()) < 0) {
            throw new EligibilityException("Not eligible for " + company.getName() + ": CGPA "
                    + student.getCgpa() + " is below the required minimum of " + company.getMinCgpa() + ".");
        }
        if (student.getActiveBacklogs() > company.getMaxBacklogs()) {
            throw new EligibilityException("Not eligible for " + company.getName() + ": "
                    + student.getActiveBacklogs() + " active backlog(s) exceed the allowed maximum of "
                    + company.getMaxBacklogs() + ".");
        }
        if (applicationRepository.existsByStudent_RollNoAndCompany_CompanyId(roll, companyId)) {
            throw new DuplicateResourceException("Student " + roll + " has already applied to "
                    + company.getName() + ".");
        }

        ApplicationEntity saved = applicationRepository.save(
                new ApplicationEntity(student, company, InterviewStatus.APPLIED));
        return toResponse(saved);
    }

    @Transactional
    public StatusUpdateResponse updateInterviewStatus(int appId, InterviewStatus newStatus) {
        if (newStatus == null) {
            throw new ValidationException("A new interview status is required.");
        }
        ApplicationEntity application = applicationRepository.findById(appId)
                .orElseThrow(() -> new ResourceNotFoundException("No application found with id " + appId + "."));

        InterviewStatus current = application.getInterviewStatus();
        if (!current.canTransitionTo(newStatus)) {
            String allowed = current.nextStates().isEmpty() ? "none (final state)" : current.nextStates().toString();
            throw new InvalidStatusTransitionException("Cannot change application " + appId + " from "
                    + current + " to " + newStatus + ". Allowed next status: " + allowed + ".");
        }

        if (newStatus != InterviewStatus.OFFERED) {
            application.setInterviewStatus(newStatus);
            return new StatusUpdateResponse(toResponse(application), false, 0);
        }

        StudentEntity student = application.getStudent();
        CompanyEntity company = application.getCompany();
        String rollNo = student.getRollNo();

        if (student.getPlacementStatus() == PlacementStatus.OPTED_OUT) {
            throw new EligibilityException("Student " + rollNo
                    + " has opted out of placements; an offer cannot be released.");
        }
        if (student.getPlacementStatus() == PlacementStatus.PLACED) {
            BigDecimal currentBest = applicationRepository.findHighestOfferedPackage(rollNo, InterviewStatus.OFFERED);
            if (currentBest == null) {
                currentBest = BigDecimal.ZERO;
            }
            if (company.getPackageLpa().compareTo(currentBest) <= 0) {
                throw new EligibilityException("Student " + rollNo + " is already PLACED with a package of "
                        + currentBest + " LPA; " + company.getName() + " (" + company.getPackageLpa()
                        + " LPA) is not a higher offer.");
            }
        }

        application.setInterviewStatus(InterviewStatus.OFFERED);
        boolean newlyPlaced = student.getPlacementStatus() != PlacementStatus.PLACED;
        if (newlyPlaced) {
            student.setPlacementStatus(PlacementStatus.PLACED);
        }
        ApplicationResponse response = toResponse(application);
        int cancelled = applicationRepository.rejectPendingApplicationsForLowerPackages(
                rollNo, company.getPackageLpa());
        return new StatusUpdateResponse(response, newlyPlaced, cancelled);
    }

    @Transactional(readOnly = true)
    public ApplicationDetail getApplicationDetail(int appId) {
        ApplicationEntity application = applicationRepository.findById(appId)
                .orElseThrow(() -> new ResourceNotFoundException("No application found with id " + appId + "."));
        return toDetail(application);
    }

    @Transactional(readOnly = true)
    public List<ApplicationDetail> listAllApplications() {
        return applicationRepository.findAllByOrderByAppIdAsc().stream()
                .map(ApplicationService::toDetail)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ApplicationDetail> listApplicationsForStudent(String rollNo) {
        String normalizedRollNo = StudentService.normalizeRollNo(rollNo);
        if (!studentRepository.existsByRollNo(normalizedRollNo)) {
            throw new ResourceNotFoundException("No student found with roll number " + normalizedRollNo + ".");
        }
        return applicationRepository.findByStudent_RollNoOrderByAppIdAsc(normalizedRollNo).stream()
                .map(ApplicationService::toDetail)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ApplicationDetail> listApplicationsForCompany(int companyId) {
        if (!companyRepository.existsById(companyId)) {
            throw new ResourceNotFoundException("No company found with id " + companyId + ".");
        }
        return applicationRepository.findByCompany_CompanyIdOrderByAppIdAsc(companyId).stream()
                .map(ApplicationService::toDetail)
                .toList();
    }

    private static ApplicationDetail toDetail(ApplicationEntity application) {
        StudentEntity student = application.getStudent();
        CompanyEntity company = application.getCompany();
        return new ApplicationDetail(application.getAppId(), student.getRollNo(), student.getName(),
                student.getCgpa(), company.getCompanyId(), company.getName(), company.getPackageLpa(),
                application.getInterviewStatus());
    }

    private static ApplicationResponse toResponse(ApplicationEntity application) {
        return new ApplicationResponse(application.getAppId(), application.getStudent().getRollNo(),
                application.getCompany().getCompanyId(), application.getInterviewStatus());
    }
}
