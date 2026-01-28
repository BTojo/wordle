package ru.wordle.infrastructure.repository;

import ru.wordle.infrastructure.entity.GameEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public class GameEntityRepositoryJpa implements GameEntityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<GameEntity> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(GameEntity.class, id));
    }
}
