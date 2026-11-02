package com.squad.universityplacement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse applyForDrive(@Valid @RequestBody ApplicationRequest request) {
        return applicationService.applyForDrive(request.getRollNo(), request.getCompanyId());
    }

    @GetMapping
    public List<ApplicationDetail> listApplications(
            @RequestParam(required = false) String rollNo,
            @RequestParam(required = false) Integer companyId) {
        if (rollNo != null && companyId != null) {
            return applicationService.listApplicationsForStudent(rollNo).stream()
                    .filter(application -> application.getCompanyId() == companyId)
                    .toList();
        }
        if (rollNo != null) {
            return applicationService.listApplicationsForStudent(rollNo);
        }
        if (companyId != null) {
            return applicationService.listApplicationsForCompany(companyId);
        }
        return applicationService.listAllApplications();
    }

    @GetMapping("/{appId}")
    public ApplicationDetail getApplication(@PathVariable int appId) {
        return applicationService.getApplicationDetail(appId);
    }

    @PatchMapping("/{appId}/status")
    public StatusUpdateResponse updateStatus(@PathVariable int appId,
                                             @Valid @RequestBody StatusUpdateRequest request) {
        return applicationService.updateInterviewStatus(appId, request.getStatus());
    }
}