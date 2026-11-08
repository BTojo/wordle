package ru.wordle.infrastructure.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameFinishedEventDto {

    private String gameId;
    private String ownerId;
    private String gameStatus;
}