package ru.wordle.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wordle.api.session.GameSession;
import ru.wordle.domain.exception.InvalidWordException;
import ru.wordle.domain.exception.UserNotStartedGameException;
import ru.wordle.domain.exception.WordNotInDictionaryException;
import ru.wordle.domain.model.Game;
import ru.wordle.domain.service.DomainGameService;
import ru.wordle.infrastructure.datastorage.Storage;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ApplicationGameService {

    private final DomainGameService domainService;
    private final Storage storage;
    private final GameSession gameSession;

    public Game startNewGame() {
        String secretWord = storage.getRandomWord();
        Game game = domainService.createGame(secretWord);
        gameSession.set(game);
        return game;
    }

    public Game guess(String guess) {
        Game game = gameSession.get();
        if (game == null) {
            throw new UserNotStartedGameException();
        }

        String normalized = guess.trim();
        if (normalized.length() != 5) {
            throw new InvalidWordException("guess must be 5 letters");
        }
        if (!normalized.matches("^[A-Za-z]+$")) {
            throw new InvalidWordException("guess must contain only letters");
        }

        String lower = normalized.toLowerCase(Locale.ROOT);


        if (!storage.isExists(lower)) {
            throw new WordNotInDictionaryException(lower);
        }

        Game updatedGame = domainService.makeAttempt(game, guess);

        gameSession.set(updatedGame);

        return updatedGame;
    }

    public Game getCurrentGame() {
        Game game = gameSession.get();
        if (game == null) {
            throw new UserNotStartedGameException();
        }
        return game;
    }
}
