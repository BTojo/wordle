package ru.wordle.domain.repository;

import ru.wordle.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);
}