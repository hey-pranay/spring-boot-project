package com.squad.universityplacement.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ApplicationRequest {

    @NotBlank
    @Size(max = 30)
    private String rollNo;

    @Positive
    private int companyId;

    public ApplicationRequest() {
    }

    public ApplicationRequest(String rollNo, int companyId) {
        this.rollNo = rollNo;
        this.companyId = companyId;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
