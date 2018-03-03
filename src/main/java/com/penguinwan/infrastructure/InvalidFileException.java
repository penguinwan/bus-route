package com.penguinwan.infrastructure;

/**
 * Error when a given data file is invalid.
 */
public class InvalidFileException extends Exception {
    /**
     * Constructor.
     *
     * @param message
     */
    public InvalidFileException(String message) {
        super(message);
    }
}
