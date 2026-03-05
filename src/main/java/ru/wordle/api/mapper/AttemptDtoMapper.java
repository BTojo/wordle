package ru.wordle.api.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.wordle.api.dto.AttemptDto;
import ru.wordle.domain.model.Attempt;

@Component
@RequiredArgsConstructor
public class AttemptDtoMapper {

    private final LetterMapper letterMapper;

    public AttemptDto toDto(Attempt attempt) {
        if (attempt == null) {
            return null;
        }

        AttemptDto dto = new AttemptDto();
        dto.setAttemptNumber(attempt.getAttemptNumber());

        if (attempt.getLetters() != null && !attempt.getLetters().isEmpty()) {
            dto.setLetters(
                    attempt.getLetters()
                            .stream()
                            .map(letterMapper::toDto)
                            .toList()
            );
        }

        return dto;
    }
}