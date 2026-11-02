package com.squad.universityplacement.api;

public class StatusUpdateResponse {

    private final ApplicationResponse application;
    private final boolean studentMarkedPlaced;
    private final int cancelledApplications;

    public StatusUpdateResponse(ApplicationResponse application, boolean studentMarkedPlaced,
                                int cancelledApplications) {
        this.application = application;
        this.studentMarkedPlaced = studentMarkedPlaced;
        this.cancelledApplications = cancelledApplications;
    }

    public ApplicationResponse getApplication() {
        return application;
    }

    public boolean isStudentMarkedPlaced() {
        return studentMarkedPlaced;
    }

    public int getCancelledApplications() {
        return cancelledApplications;
    }
}
