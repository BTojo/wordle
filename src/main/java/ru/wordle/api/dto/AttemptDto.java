package ru.wordle.api.dto;

import lombok.Data;
import ru.wordle.api.dto.LetterDto;

import java.util.List;

@Data
public class AttemptDto {
    private int attemptNumber;
    private List<LetterDto> letters;
}
