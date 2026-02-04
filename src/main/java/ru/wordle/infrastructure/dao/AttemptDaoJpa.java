package ru.wordle.infrastructure.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.wordle.domain.model.Attempt;
import ru.wordle.infrastructure.entity.AttemptEntity;
import ru.wordle.infrastructure.entity.LetterEntity;

import java.util.ArrayList;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AttemptDaoJpa implements AttemptDao {

    private final AttemptJpaRepository attemptJpaRepository;

    @Override
    public void save(Attempt attempt) {

        AttemptEntity entity = new AttemptEntity();
        entity.setGameId(UUID.fromString(attempt.getGameId()));
        entity.setAttemptNumber(attempt.getAttemptNumber());

        var letterEntities = new ArrayList<LetterEntity>();

        attempt.getLetters().forEach(letter -> {
            LetterEntity le = new LetterEntity();
            le.setAttempt(entity);
            le.setAttemptId(null); // будет заполнен позже
            le.setLetter(String.valueOf(letter.getValue()));
            le.setStatus(letter.getStatus());
            letterEntities.add(le);
        });

        entity.setLetters(letterEntities);

        attemptJpaRepository.save(entity);

        attempt.setId(entity.getId().toString());
    }
}