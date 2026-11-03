package com.squad.universityplacement.api;

import com.squad.universityplacement.entity.InterviewStatus;
import jakarta.validation.constraints.NotNull;

public class StatusUpdateRequest {

    @NotNull
    private InterviewStatus status;

    public StatusUpdateRequest() {
    }

    public StatusUpdateRequest(InterviewStatus status) {
        this.status = status;
    }

    public InterviewStatus getStatus() {
        return status;
    }

    public void setStatus(InterviewStatus status) {
        this.status = status;
    }
}