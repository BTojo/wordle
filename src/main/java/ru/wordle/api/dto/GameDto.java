package ru.wordle.api.dto;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.*;
import ru.wordle.domain.model.GameStatus;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {
    private GameStatus status;
    @Setter
    private List<AttemptDto> attempts;
    private String GameId;
    @Setter
    @Getter
    private OffsetDateTime createdAt;

}