package ru.wordle.infrastructure.repository;

import ru.wordle.infrastructure.entity.AttemptEntity;

import java.util.List;
import java.util.UUID;

public interface AttemptRepository {

    AttemptEntity save(AttemptEntity attempt);

    List<AttemptEntity> findByGameId(UUID gameId);
}
