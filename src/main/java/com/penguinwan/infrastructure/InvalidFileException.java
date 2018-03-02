package com.penguinwan.infrastructure;

public class InvalidFileException extends Exception {
    public InvalidFileException(String message) {
        super(message);
    }

    public InvalidFileException(String message, Throwable error) {
        super(message, error);
    }
}
