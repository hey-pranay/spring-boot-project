package com.squad.universityplacement.api;

import com.university.placement.entity.InterviewStatus;

public class ApplicationResponse<InterviewStatus> {

    private final Integer appId;
    private final String rollNo;
    private final int companyId;
    private final InterviewStatus interviewStatus;

    public ApplicationResponse(Integer appId, String rollNo, int companyId, InterviewStatus interviewStatus) {
        this.appId = appId;
        this.rollNo = rollNo;
        this.companyId = companyId;
        this.interviewStatus = interviewStatus;
    }

    public Integer getAppId() {
        return appId;
    }

    public String getRollNo() {
        return rollNo;
    }

    public int getCompanyId() {
        return companyId;
    }

    public InterviewStatus getInterviewStatus() {
        return interviewStatus;
    }
}
