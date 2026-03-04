package ru.wordle.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.wordle.domain.model.Attempt;
import ru.wordle.domain.model.Letter;
import ru.wordle.domain.repository.AttemptRepository;
import ru.wordle.infrastructure.entity.AttemptEntity;
import ru.wordle.infrastructure.entity.LetterEntity;
import ru.wordle.infrastructure.mapper.LetterEntityMapper;
import ru.wordle.infrastructure.repository.dao.AttemptJpaRepository;

import java.util.Objects;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AttemptRepositoryJpa implements AttemptRepository {

    private final AttemptJpaRepository attemptJpaRepository;
    private final LetterEntityMapper letterMapper;

    @Override
    @Transactional
    public void save(Attempt attempt) {

        AttemptEntity attemptEntity = new AttemptEntity();

        if (Objects.isNull(attempt.getId())) {
            attemptEntity.setId(UUID.randomUUID());
            attemptEntity.setNew(true);
            attempt.setId(attemptEntity.getId().toString());
        } else {
            attemptEntity.setId(UUID.fromString(attempt.getId()));
        }

        attemptEntity.setGameId(UUID.fromString(attempt.getGameId()));
        attemptEntity.setAttemptNumber(attempt.getAttemptNumber());

        for (Letter letter : attempt.getLetters()) {

            LetterEntity letterEntity = letterMapper.toEntity(letter);

            attemptEntity.addLetter(letterEntity);
        }

        attemptJpaRepository.save(attemptEntity);

        attempt.setId(attemptEntity.getId().toString());
    }
}