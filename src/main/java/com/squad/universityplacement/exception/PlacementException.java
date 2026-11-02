package com.squad.universityplacement.exception;

/** Base type for all business-rule failures; its message is safe to show to the console user. */
public class PlacementException extends RuntimeException {

    public PlacementException(String message) {
        super(message);
    }
}
