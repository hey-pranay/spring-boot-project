package com.squad.universityplacement.exception;

/** The requested resource already exists. */
public class DuplicateResourceException extends PlacementException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
