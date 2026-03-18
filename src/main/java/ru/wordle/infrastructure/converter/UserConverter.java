package ru.wordle.infrastructure.converter;

import org.springframework.stereotype.Component;
import ru.wordle.domain.model.User;
import ru.wordle.domain.model.UserStatus;
import ru.wordle.infrastructure.entity.UserEntity;

import java.time.ZoneOffset;

@Component
public class UserConverter {

    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setNew(true);
        entity.setLogin(user.getLogin());
        entity.setPassword(user.getPassword());
        entity.setStatus(user.getStatus() != null ? user.getStatus().name() : null);
        entity.setCreatedAt(user.getCreatedAt() != null
                ? user.getCreatedAt().atOffset(ZoneOffset.UTC)
                : null);
        return entity;
    }

    public User toModel(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setLogin(entity.getLogin());
        user.setPassword(entity.getPassword());
        user.setStatus(entity.getStatus() != null
                ? UserStatus.valueOf(entity.getStatus())
                : null);
        user.setCreatedAt(entity.getCreatedAt() != null
                ? entity.getCreatedAt().toLocalDateTime()
                : null);
        return user;
    }
}