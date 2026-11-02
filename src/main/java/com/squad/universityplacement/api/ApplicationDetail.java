package com.squad.universityplacement.api;

import com.university.placement.entity.InterviewStatus;

import java.math.BigDecimal;

public class ApplicationDetail {

    private final int appId;
    private final String rollNo;
    private final String studentName;
    private final BigDecimal cgpa;
    private final int companyId;
    private final String companyName;
    private final BigDecimal packageLpa;
    private final InterviewStatus interviewStatus;

    public ApplicationDetail(int appId, String rollNo, String studentName, BigDecimal cgpa,
                             int companyId, String companyName, BigDecimal packageLpa,
                             InterviewStatus interviewStatus) {
        this.appId = appId;
        this.rollNo = rollNo;
        this.studentName = studentName;
        this.cgpa = cgpa;
        this.companyId = companyId;
        this.companyName = companyName;
        this.packageLpa = packageLpa;
        this.interviewStatus = interviewStatus;
    }

    public int getAppId() {
        return appId;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public BigDecimal getCgpa() {
        return cgpa;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public BigDecimal getPackageLpa() {
        return packageLpa;
    }

    public InterviewStatus getInterviewStatus() {
        return interviewStatus;
    }
}
