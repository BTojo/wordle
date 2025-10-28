package ru.wordle.api.error;

public class UserNotStartedGameException extends RuntimeException {
    public UserNotStartedGameException() {
        super("game not started");
    }
}
