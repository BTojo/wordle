package ru.wordle.infrastructure.mapper;

import org.springframework.stereotype.Component;
import ru.wordle.domain.model.Letter;
import ru.wordle.infrastructure.entity.LetterEntity;

import java.util.UUID;

@Component
public final class LetterMapper {

    private LetterMapper() {
    }

    public LetterEntity toEntity(Letter letter, UUID attemptId) {
        LetterEntity entity = new LetterEntity();
        entity.setAttemptId(attemptId);
        entity.setLetter(String.valueOf(letter.getValue()));
        entity.setStatus(letter.getStatus());
        return entity;
    }

    public Letter toDomain(LetterEntity entity) {
        Letter letter = new Letter();
        letter.setValue(entity.getLetter().charAt(0));
        letter.setStatus(entity.getStatus());
        return letter;
    }
}
