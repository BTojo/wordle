package ru.wordle.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import ru.wordle.domain.exception.AttemptValidateException;
import ru.wordle.domain.exception.MakeAttemptException;
import ru.wordle.domain.exception.UserNotStartedGameException;
import ru.wordle.domain.model.Game;
import ru.wordle.domain.model.MakeAttemptResult;
import ru.wordle.domain.repository.AttemptRepository;
import ru.wordle.domain.repository.GameRepository;
import ru.wordle.domain.repository.WordRepository;
import ru.wordle.domain.validator.AttemptValidateError;
import ru.wordle.domain.validator.AttemptValidator;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationGameService {

    private final WordRepository wordRepository;
    private final GameRepository gameRepository;
    private final AttemptRepository attemptRepository;
    private final AttemptValidator attemptValidator;

    public Game startNewGame(String ownerId) {
        String secretWord = wordRepository.getRandomWord();

        Game game = new Game(secretWord);
        game.setOwnerId(ownerId);

        return gameRepository.save(game);
    }

    @Transactional
    public void assignOwnerIfGameExists(String gameId, UUID ownerId) {
        if (gameId == null || gameId.trim().isEmpty() || ownerId == null) {
            return;
        }

        gameRepository.findById(gameId.trim())
                .ifPresent(game -> {
                    game.setOwnerId(ownerId.toString());
                    gameRepository.save(game);
                });
    }

    public Game guess(String gameId, String guess) {
        if (gameId == null || gameId.trim().isEmpty()) {
            throw new UserNotStartedGameException("Game ID cannot be null or empty");
        }

        Game game = gameRepository.findById(gameId.trim())
                .orElseThrow(() -> new UserNotStartedGameException(
                        "Game not found: " + gameId
                ));

        AttemptValidateError validateError = attemptValidator.validate(guess);
        if (!ObjectUtils.isEmpty(validateError)) {
            throw new AttemptValidateException(validateError);
        }

        MakeAttemptResult result = game.makeAttempt(
                guess.trim().toLowerCase(new Locale("ru"))
        );

        if (result.isHasError()) {
            throw new MakeAttemptException(result.getError());
        }

        attemptRepository.save(result.getAttempt());
        gameRepository.save(game);

        return game;
    }

    public Game getCurrentGame(String gameId) {
        if (gameId == null || gameId.trim().isEmpty()) {
            throw new UserNotStartedGameException("Game ID cannot be null or empty");
        }

        return gameRepository.findById(gameId.trim())
                .orElseThrow(() -> new UserNotStartedGameException(
                        "Game not found: " + gameId
                ));
    }
}