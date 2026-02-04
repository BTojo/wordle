package ru.wordle.infrastructure.mapper;

import ru.wordle.domain.model.Letter;
import ru.wordle.infrastructure.entity.LetterEntity;

import java.util.UUID;

public final class LetterMapper {

    private LetterMapper() {
    }

    public static LetterEntity toEntity(Letter letter, UUID attemptId) {
        LetterEntity entity = new LetterEntity();
        entity.setAttemptId(attemptId);
        entity.setLetter(String.valueOf(letter.getValue()));
        entity.setStatus(letter.getStatus());
        return entity;
    }
}
