package com.squad.universityplacement.entity;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum InterviewStatus {
    APPLIED,
    SHORTLISTED,
    INTERVIEWING,
    OFFERED,
    REJECTED;

    public Set<InterviewStatus> nextStates() {
        return switch (this) {
            case APPLIED -> EnumSet.of(SHORTLISTED, REJECTED);
            case SHORTLISTED -> EnumSet.of(INTERVIEWING, REJECTED);
            case INTERVIEWING -> EnumSet.of(OFFERED, REJECTED);
            case OFFERED, REJECTED -> EnumSet.noneOf(InterviewStatus.class);
        };
    }

    public boolean canTransitionTo(InterviewStatus next) {
        return nextStates().contains(next);
    }

    public boolean isPending() {
        return this == APPLIED || this == SHORTLISTED || this == INTERVIEWING;
    }

    public static Optional<InterviewStatus> parse(String text) {
        if (text == null) {
            return Optional.empty();
        }
        try {
            return Optional.of(valueOf(text.trim().toUpperCase()));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }
}
