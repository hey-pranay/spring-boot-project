package com.squad.universityplacement.exception;

/** The student is not allowed to apply for / receive the offer. */
public class EligibilityException extends PlacementException {

    public EligibilityException(String message) {
        super(message);
    }
}
