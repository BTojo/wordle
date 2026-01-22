package ru.wordle.domain.exception;

public class UserNotStartedGameException extends RuntimeException {
    public UserNotStartedGameException(String s) {
        super("game not started");
    }
}
