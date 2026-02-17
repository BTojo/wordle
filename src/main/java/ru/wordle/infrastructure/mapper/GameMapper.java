package ru.wordle.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.wordle.domain.model.Game;
import ru.wordle.domain.model.GameStatus;
import ru.wordle.infrastructure.entity.GameEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.Hibernate;
import ru.wordle.infrastructure.entity.AttemptEntity;
import ru.wordle.domain.model.Attempt;
import ru.wordle.domain.model.Letter;
import ru.wordle.infrastructure.entity.LetterEntity;
import ru.wordle.domain.model.Letter;

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

    private Attempt toDomain(AttemptEntity entity) {
        Attempt attempt = new Attempt();

        attempt.setId(entity.getId().toString());
        attempt.setGameId(entity.getGameId().toString());
        attempt.setAttemptNumber(entity.getAttemptNumber());

        if (entity.isLettersInitialized()) {
            List<Letter> letters = entity.getLetters().stream()
                    .map(this::toDomain)
                    .toList();
            attempt.setLetters(letters);
        }

        return attempt;
    }

    private Letter toDomain(LetterEntity entity) {
        Letter letter = new Letter();
        letter.setValue(entity.getLetter().charAt(0));
        letter.setStatus(entity.getStatus());
        return letter;
    }

}
