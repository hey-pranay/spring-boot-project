package com.squad.universityplacement.exception;

/** Input data is missing or invalid. */
public class ValidationException extends PlacementException {

    public ValidationException(String message) {
        super(message);
    }
}
