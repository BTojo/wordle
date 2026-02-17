package ru.wordle.infrastructure.repository.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.wordle.infrastructure.entity.GameEntity;

import java.util.Optional;
import java.util.UUID;

public interface GameDao extends JpaRepository<GameEntity, UUID> {

    @EntityGraph(attributePaths = {"attemptEntities", "attemptEntities.letters"})
    @Query("select g from GameEntity g where g.id = :id")
    Optional<GameEntity> findById(@Param("id") UUID id);
}
