package ru.wordle.infrastructure.mapper;

import org.springframework.stereotype.Component;
import ru.wordle.domain.model.Game;
import ru.wordle.domain.model.GameStatus;
import ru.wordle.infrastructure.entity.GameEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class GameMapper {

    public Game toDomain(GameEntity entity) {
        if (entity == null) {
            return null;
        }

        Game game = new Game(entity.getSecretWord());
        game.setGameId(entity.getId().toString());
        game.setGameStatus(entity.getStatus());
        game.setCreatedAt(entity.getCreatedAt());

        return game;
    }

    public GameEntity toEntity(Game domain) {
        if (domain == null) {
            return null;
        }

        GameEntity entity = new GameEntity();

        if (domain.getGameId() != null && !domain.getGameId().isEmpty()) {
            entity.setId(UUID.fromString(domain.getGameId()));
        }

        entity.setSecretWord(domain.getSecretWord());
        entity.setStatus(domain.getGameStatus());
        entity.setCreatedAt(domain.getCreatedAt() != null ?
                domain.getCreatedAt() : OffsetDateTime.now());

        return entity;
    }
}
