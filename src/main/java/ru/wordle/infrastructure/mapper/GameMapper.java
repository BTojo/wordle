package ru.wordle.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.wordle.domain.model.Game;
import ru.wordle.infrastructure.entity.GameEntity;
import java.time.OffsetDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GameMapper {

    private final AttemptMapper attemptMapper;

    public Game toDomain(GameEntity entity) {
        if (entity == null) {
            return null;
        }

        Game game = new Game(entity.getSecretWord());
        game.setGameId(entity.getId().toString());
        game.setGameStatus(entity.getStatus());
        game.setCreatedAt(entity.getCreatedAt());

        if (entity.isAttemptsInitialized()) {
            entity.getAttemptEntities()
                    .forEach(attemptEntity ->
                            game.getAttemptsList()
                                    .add(attemptMapper.toDomain(attemptEntity))
                    );
        }

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
