package ru.wordle.domain.exception;

public class LoginAlreadyTakenException extends RuntimeException {

    public LoginAlreadyTakenException(String login) {
        super("Login already taken: " + login);
    }
}