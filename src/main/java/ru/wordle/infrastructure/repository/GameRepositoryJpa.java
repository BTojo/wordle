package ru.wordle.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.wordle.domain.model.Game;
import ru.wordle.infrastructure.entity.GameEntity;
import ru.wordle.infrastructure.repository.dao.GameDao;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@Profile("jpa")
@RequiredArgsConstructor
@Slf4j
public class GameRepositoryJpa implements GameRepository {

    private final GameDao gameDao;

    @Override
    @Transactional
    public void save(Game game) {
        GameEntity entity = new GameEntity();


        entity.setId(UUID.fromString(game.getGameId()));

        entity.setSecretWord(game.getSecretWord());
        entity.setStatus(game.getStatus().name());


        entity.setCreatedAt(
                game.getCreatedAt() != null ? game.getCreatedAt() : LocalDateTime.now()
        );

        gameDao.save(entity);

        log.info("Game {} saved with status {}", entity.getId(), entity.getStatus());
    }
}
