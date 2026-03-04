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

        LetterDto dto = new LetterDto();
        dto.setLetter(letter.getValue());
        dto.setColor(letter.getStatus().name());

        return dto;
    }

    public LetterEntity toEntity(Letter letter, AttemptEntity attempt) {
        if (letter == null) {
            return null;
        }

        LetterEntity entity = new LetterEntity();
        entity.setId(UUID.randomUUID());           // ID сущности
        //  entity.setAttemptId(attempt.getId());       // ID попытки (внешний ключ)
        entity.setLetter(String.valueOf(letter.getValue())); // буква
        entity.setPosition(letter.getPosition());   // ИСПРАВЛЕНО: позиция буквы
        entity.setStatus(letter.getStatus());       // статус

        return entity;
    }
}