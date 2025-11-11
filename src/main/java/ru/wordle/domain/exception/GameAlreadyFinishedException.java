package ru.wordle.domain.exception;

public class GameAlreadyFinishedException extends RuntimeException {

    public GameAlreadyFinishedException() {
        super("cannot make attempt in finished game");
    }

    public GameAlreadyFinishedException(String message) {
        super(message);
    }

    public GameAlreadyFinishedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameAlreadyFinishedException(Throwable cause) {
        super(cause);
    }
}
