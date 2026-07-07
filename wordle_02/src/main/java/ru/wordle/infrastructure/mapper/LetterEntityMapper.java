package ru.wordle.infrastructure.mapper;

import org.springframework.stereotype.Component;
import ru.wordle.domain.model.Letter;
import ru.wordle.infrastructure.entity.LetterEntity;

import java.util.UUID;

@Component
public class LetterEntityMapper {

    public Letter toDomain(LetterEntity entity) {
        if (entity == null) {
            return null;
        }

        Letter letter = new Letter();
        letter.setValue(entity.getLetter().charAt(0));
        letter.setStatus(entity.getStatus());
        letter.setPosition(entity.getPosition());

        return letter;
    }

    public LetterEntity toEntity(Letter letter) {
        if (letter == null) {
            return null;
        }

        LetterEntity entity = new LetterEntity();
        entity.setId(UUID.randomUUID());
        entity.setLetter(String.valueOf(letter.getValue()));
        entity.setPosition(letter.getPosition());
        entity.setStatus(letter.getStatus());

        return entity;
    }
}