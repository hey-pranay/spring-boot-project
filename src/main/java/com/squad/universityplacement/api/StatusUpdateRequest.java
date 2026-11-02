package com.squad.universityplacement.api;

import com.university.placement.entity.InterviewStatus;
import jakarta.validation.constraints.NotNull;
import org.jetbrains.annotations.NotNull;

public class StatusUpdateRequest {

    @org.jetbrains.annotations.NotNull
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
