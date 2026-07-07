package ru.wordle.domain.model;

import lombok.Getter;

@Getter
public class MakeAttemptResult {

    private Attempt attempt;

    private MakeAttemptError error;

    public static MakeAttemptResult success(Attempt attempt) {
        MakeAttemptResult result = new MakeAttemptResult();
        result.attempt = attempt;
        return result;
    }

    public static MakeAttemptResult failure(MakeAttemptError error) {
        MakeAttemptResult result = new MakeAttemptResult();
        result.error = error;
        return result;
    }

    public boolean isHasError() {
        return error != null;
    }
}