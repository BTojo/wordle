package ru.wordle.domain.exception;

public class UserNotStartedGameException extends RuntimeException {
    public UserNotStartedGameException() {
        super("game not started");
    }
}
