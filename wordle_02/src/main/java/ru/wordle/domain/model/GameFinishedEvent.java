package ru.wordle.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameFinishedEvent {

    private String gameId;
    private String ownerId;
    private GameStatus gameStatus;
}