package ru.wordle.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.wordle.domain.model.User;
import ru.wordle.domain.repository.UserRepository;
import ru.wordle.infrastructure.converter.UserConverter;
import ru.wordle.infrastructure.repository.dao.UserJpaRepository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserRepositoryJpa implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserConverter userConverter;

    @Override
    public void save(User user) {
        userJpaRepository.save(userConverter.toEntity(user));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userJpaRepository.findByLogin(login)
                .map(userConverter::toModel);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id).map(userConverter::toModel);
    }

    @Override
    public boolean existsByLogin(String login) {
        return userJpaRepository.existsByLogin(login);
    }
}