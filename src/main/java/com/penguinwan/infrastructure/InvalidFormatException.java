package com.penguinwan.infrastructure;

/**
 * Error when a given data file content is invalid.
 */
public class InvalidFormatException extends Exception {
    /**
     * Constructor.
     *
     * @param message
     */
    public InvalidFormatException(String message) {
        super(message);
    }
}
