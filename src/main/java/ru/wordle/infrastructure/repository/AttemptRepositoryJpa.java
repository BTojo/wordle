package ru.wordle.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.wordle.domain.model.Attempt;
import ru.wordle.domain.model.Letter;
import ru.wordle.domain.repository.AttemptRepository;
import ru.wordle.infrastructure.entity.AttemptEntity;
import ru.wordle.infrastructure.entity.LetterEntity;
import ru.wordle.infrastructure.repository.dao.AttemptJpaRepository;
import ru.wordle.infrastructure.mapper.LetterMapper;

import java.util.ArrayList;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AttemptRepositoryJpa implements AttemptRepository {

    private final AttemptJpaRepository attemptJpaRepository;
    private final LetterMapper letterMapper;

    @Override
    @Transactional
    public void save(Attempt attempt) {

        AttemptEntity attemptEntity = new AttemptEntity();
        attemptEntity.setId(UUID.randomUUID());
        attemptEntity.setGameId(UUID.fromString(attempt.getGameId()));
        attemptEntity.setAttemptNumber(attempt.getAttemptNumber());

        var letterEntities = new ArrayList<LetterEntity>();
        for (Letter letter : attempt.getLetters()) {
            letterEntities.add(
                    letterMapper.toEntity(letter, attemptEntity.getId())
            );
        }
        attemptEntity.setLetters(letterEntities);

        attemptJpaRepository.save(attemptEntity);

        attempt.setId(attemptEntity.getId().toString());
    }
}
