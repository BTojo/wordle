package ru.wordle.infrastructure.kafka.converter;

import org.springframework.stereotype.Component;
import ru.wordle.domain.model.Game;
import ru.wordle.domain.model.GameFinishedEvent;
import ru.wordle.infrastructure.kafka.dto.GameFinishedEventDto;

@Component
public class GameFinishedEventConverter {

    public GameFinishedEventDto convert(GameFinishedEvent event) {
        Game game = event.getGame();

        return new GameFinishedEventDto(
                game.getGameId(),
                game.getOwnerId(),
                game.getGameStatus().name()
        );
    }
}