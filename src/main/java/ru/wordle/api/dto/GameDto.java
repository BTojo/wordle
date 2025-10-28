package ru.wordle.api.dto;

import java.util.List;

import lombok.*;
import ru.wordle.domain.model.GameStatus;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {
    private GameStatus status;
    private List<List<LetterDto>> attempts;
}