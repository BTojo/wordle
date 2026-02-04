package ru.wordle.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.wordle.domain.model.Attempt;
import ru.wordle.domain.model.Letter;
import ru.wordle.domain.repository.AttemptRepository;
import ru.wordle.infrastructure.entity.AttemptEntity;
import ru.wordle.infrastructure.entity.LetterEntity;
import ru.wordle.infrastructure.mapper.LetterMapper;

import java.util.ArrayList;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AttemptRepositoryJpa implements AttemptRepository {

    private final AttemptJpaRepository attemptJpaRepository;

    @Override
    public void save(Attempt attempt) {

        AttemptEntity attemptEntity = new AttemptEntity();
        attemptEntity.setGameId(UUID.fromString(attempt.getGameId()));
        attemptEntity.setAttemptNumber(attempt.getAttemptNumber());

        attemptJpaRepository.saveAndFlush(attemptEntity);

        attempt.setId(attemptEntity.getId().toString());

        var letterEntities = new ArrayList<LetterEntity>();

        for (Letter letter : attempt.getLetters()) {
            letterEntities.add(
                    LetterMapper.toEntity(letter, attemptEntity.getId())
            );
        }

        attemptEntity.setLetters(letterEntities);
        attemptJpaRepository.save(attemptEntity);
    }
}
