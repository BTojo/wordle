package ru.wordle.api.mapper;

import org.springframework.stereotype.Component;
import ru.wordle.api.dto.LetterDto;
import ru.wordle.domain.model.Letter;
import ru.wordle.infrastructure.entity.AttemptEntity;
import ru.wordle.infrastructure.entity.LetterEntity;

import java.util.UUID;

@Component
public class LetterMapper {

    public LetterDto toDto(Letter letter) {
        if (letter == null) {
            return null;
        }
        return new LetterDto(
                String.valueOf(letter.getValue()),
                letter.getStatus().name()
        );
    }

    public LetterEntity toEntity(Letter letter, AttemptEntity attempt) {
        if (letter == null) {
            return null;
        }

        LetterEntity entity = new LetterEntity();
        entity.setId(UUID.randomUUID());
        entity.setLetter(String.valueOf(letter.getValue()));
        entity.setPosition(letter.getPosition());
        entity.setStatus(letter.getStatus());
        entity.setAttempt(attempt);

        return entity;
    }
}