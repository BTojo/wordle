package ru.wordle.infrastructure.repository.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.wordle.infrastructure.entity.GameEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameDao extends JpaRepository<GameEntity, UUID> {

    @EntityGraph(attributePaths = {"attemptEntities"})
    @Query("select g from GameEntity g where g.id = :id")
    Optional<GameEntity> findById(@Param("id") UUID id);
}
