package ru.wordle.domain.exception;

import lombok.Getter;
import ru.wordle.domain.model.MakeAttemptError;

@Getter
public class MakeAttemptException extends RuntimeException {

    private final MakeAttemptError error;

    public MakeAttemptException(MakeAttemptError error) {
        super(error.name());
        this.error = error;
    }
}