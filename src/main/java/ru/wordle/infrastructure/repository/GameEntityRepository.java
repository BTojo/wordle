package ru.wordle.infrastructure.repository;

import ru.wordle.infrastructure.entity.GameEntity;

import java.util.Optional;
import java.util.UUID;

public interface GameEntityRepository {

    Optional<GameEntity> findById(UUID id);
}
