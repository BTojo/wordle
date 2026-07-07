package ru.wordle.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.wordle.domain.model.Game;
import ru.wordle.domain.repository.GameRepository;
import ru.wordle.infrastructure.entity.AttemptEntity;
import ru.wordle.infrastructure.entity.GameEntity;
import ru.wordle.infrastructure.mapper.GameMapper;
import ru.wordle.infrastructure.repository.dao.AttemptJpaRepository;
import ru.wordle.infrastructure.repository.dao.GameDao;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
//@Profile("jpa")
@RequiredArgsConstructor
@Slf4j
public class GameRepositoryJpa implements GameRepository {

    private final GameDao gameDao;
    private final AttemptJpaRepository attemptJpaRepository;
    private final GameMapper gameMapper;

    @Override
    @Transactional
    public Game save(Game game) {
        GameEntity entity = gameMapper.toEntity(game);

        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
            entity.setNew(true);
            entity.setCreatedAt(OffsetDateTime.now());
            game.setGameId(entity.getId().toString());
        }

        GameEntity savedEntity = gameDao.save(entity);
        log.info("Game {} saved with status {}", savedEntity.getId(), savedEntity.getStatus());

        return gameMapper.toDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Game> findById(String gameId) {
        if (gameId == null || gameId.isEmpty()) {
            return Optional.empty();
        }

        try {
            UUID uuid = UUID.fromString(gameId);
            GameEntity entity = gameDao.findById(uuid).orElse(null);
            if (Objects.isNull(entity)) {
                return Optional.empty();
            }
            if (entity.isAttemptsInitialized()) {
                Set<UUID> attemptIds = new HashSet<>();
                for (AttemptEntity attemptEntity : entity.getAttemptEntities()) {
                    attemptIds.add(attemptEntity.getId());
                }

                attemptJpaRepository.findByIdWithLetters(attemptIds);
            }

            return Optional.ofNullable(gameMapper.toDomain(entity));
        } catch (IllegalArgumentException e) {
            log.warn("Invalid game ID format: {}", gameId);
            return Optional.empty();
        }
    }

}