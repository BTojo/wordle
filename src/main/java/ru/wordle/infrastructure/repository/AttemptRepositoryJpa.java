package ru.wordle.infrastructure.repository;

import ru.wordle.infrastructure.entity.AttemptEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public class AttemptRepositoryJpa implements AttemptRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public AttemptEntity save(AttemptEntity attempt) {
        entityManager.persist(attempt);
        return attempt;
    }

    @Override
    @Transactional
    public List<AttemptEntity> findByGameId(UUID gameId) {
        return entityManager.createQuery(
                        "select a from AttemptEntity a where a.game.id = :gameId",
                        AttemptEntity.class
                )
                .setParameter("gameId", gameId)
                .getResultList();
    }
}
