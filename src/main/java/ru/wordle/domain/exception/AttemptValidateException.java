package ru.wordle.domain.exception;

import lombok.Getter;
import ru.wordle.domain.validator.AttemptValidateError;

@Getter
public class AttemptValidateException extends RuntimeException {

    private final AttemptValidateError error;

    public AttemptValidateException(AttemptValidateError error) {
        super(error.name());
        this.error = error;
    }
}