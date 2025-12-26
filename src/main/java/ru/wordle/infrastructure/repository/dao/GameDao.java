package ru.wordle.infrastructure.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wordle.infrastructure.entity.GameEntity;

import java.util.UUID;

public interface GameDao extends JpaRepository<GameEntity, UUID> {
}
