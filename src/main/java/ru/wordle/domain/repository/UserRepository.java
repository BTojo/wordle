package ru.wordle.domain.repository;

import ru.wordle.domain.model.User;

import java.util.*;

public interface UserRepository {

    void save(User user);

    Optional<User> findByLogin(String login);

    Optional<User> findById(UUID id);

    boolean existsByLogin(String login);
}