package ru.wordle.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.wordle.domain.model.Attempt;
import ru.wordle.infrastructure.entity.AttemptEntity;

@Component
@RequiredArgsConstructor
public class AttemptMapper {

    private final LetterEntityMapper letterMapper;

    public Attempt toDomain(AttemptEntity entity) {
        Attempt attempt = new Attempt();

        attempt.setId(entity.getId().toString());
        attempt.setGameId(entity.getGameId().toString());
        attempt.setAttemptNumber(entity.getAttemptNumber());

        if (entity.isLettersInitialized()) {
            entity.getLetters().forEach(letterEntity ->
                    attempt.getLetters().add(
                            letterMapper.toDomain(letterEntity)
                    )
            );
        }

        return attempt;
    }
}
