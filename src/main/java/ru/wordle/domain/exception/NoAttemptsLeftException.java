package ru.wordle.domain.exception;

public class NoAttemptsLeftException extends RuntimeException {

    public NoAttemptsLeftException(String message) {
        super(message);
    }
}