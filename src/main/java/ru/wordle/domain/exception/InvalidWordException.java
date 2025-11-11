package ru.wordle.domain.exception;

public class InvalidWordException extends RuntimeException {
    public InvalidWordException(String message) {
        super(message);
    }

    public InvalidWordException() {
        super("invalid word format");
    }
}
