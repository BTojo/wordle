package ru.wordle.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wordle.domain.exception.InvalidWordException;
import ru.wordle.domain.exception.UserNotStartedGameException;
import ru.wordle.domain.exception.WordNotInDictionaryException;
import ru.wordle.domain.model.Attempt;
import ru.wordle.domain.model.Game;
import ru.wordle.domain.model.Letter;
import ru.wordle.infrastructure.entity.AttemptEntity;
import ru.wordle.infrastructure.entity.LetterEntity;
import ru.wordle.infrastructure.repository.AttemptRepository;
import ru.wordle.infrastructure.repository.GameEntityRepository;
import ru.wordle.infrastructure.repository.GameRepository;
import ru.wordle.infrastructure.repository.WordRepository;

import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationGameService {

    private final WordRepository wordRepository;
    private final GameRepository gameRepository;
    private final AttemptRepository attemptRepository;
    private final GameEntityRepository gameEntityRepository;

    public Game startNewGame() {
        String secretWord = wordRepository.getRandomWord();
        Game game = new Game(secretWord);

        return gameRepository.save(game);
    }

    public Game guess(String gameId, String guess) {
        if (gameId == null || gameId.trim().isEmpty()) {
            throw new UserNotStartedGameException("Game ID cannot be null or empty");
        }

        Game game = gameRepository.findById(gameId.trim())
                .orElseThrow(() -> new UserNotStartedGameException("Game not found: " + gameId));

        if (guess == null) {
            throw new InvalidWordException("guess must not be null");
        }

        String normalized = guess.trim();
        if (normalized.length() != 5) {
            throw new InvalidWordException("guess must be 5 letters");
        }
        if (!normalized.matches("^[A-Za-z]+$")) {
            throw new InvalidWordException("guess must contain only letters");
        }

        String lower = normalized.toLowerCase(Locale.ROOT);
        if (!wordRepository.isExists(lower)) {
            throw new WordNotInDictionaryException(lower);
        }

        Attempt attempt = game.makeAttempt(lower);

        saveAttempt(game, gameId, attempt);

        return gameRepository.save(game);
    }

    public Game getCurrentGame(String gameId) {
        if (gameId == null || gameId.trim().isEmpty()) {
            throw new UserNotStartedGameException("Game ID cannot be null or empty");
        }

        return gameRepository.findById(gameId.trim())
                .orElseThrow(() -> new UserNotStartedGameException("Game not found: " + gameId));
    }

    private void saveAttempt(Game game, String gameId, Attempt attempt) {

        var gameEntity = gameEntityRepository.findById(UUID.fromString(gameId))
                .orElseThrow(() -> new IllegalStateException("GameEntity not found"));

        var attemptEntity = new AttemptEntity();
        attemptEntity.setGame(gameEntity);
        attemptEntity.setAttemptNumber(game.getAttemptsList().size());

        var letterEntities = new ArrayList<LetterEntity>();

        for (Letter letter : attempt.getLetters()) {
            var letterEntity = new LetterEntity();
            letterEntity.setLetter(String.valueOf(letter.getValue()));
            letterEntity.setStatus(letter.getStatus());
            letterEntity.setAttempt(attemptEntity);

            letterEntities.add(letterEntity);
        }

        attemptEntity.setLetters(letterEntities);

        attemptRepository.save(attemptEntity);
    }

}
