package ru.wordle.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.wordle.domain.model.User;
import ru.wordle.domain.repository.UserRepository;
import ru.wordle.infrastructure.entity.UserEntity;
import ru.wordle.infrastructure.repository.dao.UserJpaRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryJpa implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public void save(User user) {
        UserEntity entity = new UserEntity();

        entity.setId(user.getId());
        entity.setLogin(user.getLogin());
        entity.setPassword(user.getPassword());
        entity.setCreatedAt(user.getCreatedAt());

        userJpaRepository.save(entity);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userJpaRepository.findByLogin(login)
                .map(entity -> {
                    User user = new User();
                    user.setId(entity.getId());
                    user.setLogin(entity.getLogin());
                    user.setPassword(entity.getPassword());
                    user.setCreatedAt(entity.getCreatedAt());
                    return user;
                });
    }
}