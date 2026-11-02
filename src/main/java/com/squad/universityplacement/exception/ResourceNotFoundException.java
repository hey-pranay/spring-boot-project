package com.squad.universityplacement.exception;

/** A student, company or application does not exist. */
public class ResourceNotFoundException extends PlacementException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
