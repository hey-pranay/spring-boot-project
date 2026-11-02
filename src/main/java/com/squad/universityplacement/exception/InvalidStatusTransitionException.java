package com.squad.universityplacement.exception;

/** The interview status change is not permitted. */
public class InvalidStatusTransitionException extends PlacementException {

    public InvalidStatusTransitionException(String message) {
        super(message);
    }
}
