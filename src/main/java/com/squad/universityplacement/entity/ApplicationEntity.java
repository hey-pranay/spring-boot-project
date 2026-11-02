package com.squad.universityplacement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "applications", uniqueConstraints = {
        @UniqueConstraint(name = "uq_applications_student_company", columnNames = {"roll_no", "company_id"})
})
public class ApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id")
    private Integer appId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roll_no", nullable = false)
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @Enumerated(EnumType.STRING)
    @Column(name = "interview_status", nullable = false, length = 30, columnDefinition = "varchar(30)")
    private InterviewStatus interviewStatus;

    protected ApplicationEntity() {
    }

    public ApplicationEntity(StudentEntity student, CompanyEntity company, InterviewStatus interviewStatus) {
        this.student = student;
        this.company = company;
        this.interviewStatus = interviewStatus;
    }

    public Integer getAppId() {
        return appId;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public InterviewStatus getInterviewStatus() {
        return interviewStatus;
    }

    public void setInterviewStatus(InterviewStatus interviewStatus) {
        this.interviewStatus = interviewStatus;
    }
}
