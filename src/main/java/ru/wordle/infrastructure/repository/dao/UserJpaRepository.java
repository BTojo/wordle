package ru.wordle.infrastructure.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.wordle.infrastructure.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByLogin(String login);
}