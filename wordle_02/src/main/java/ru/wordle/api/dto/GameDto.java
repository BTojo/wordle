package ru.wordle.api.dto;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Data;
import ru.wordle.domain.model.GameStatus;


@Data
public class GameDto {

    private String gameId;
    private GameStatus gameStatus;
    private List<AttemptDto> attempts;
    private OffsetDateTime createDateTime;
    private String targetWord;

}