package ru.wordle.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wordle.infrastructure.entity.AttemptEntity;

import java.util.UUID;

public interface AttemptJpaRepository extends JpaRepository<AttemptEntity, UUID> {
}
