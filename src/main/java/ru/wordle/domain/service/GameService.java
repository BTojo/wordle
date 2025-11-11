package ru.wordle.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wordle.domain.exception.InvalidWordException;
import ru.wordle.domain.exception.UserNotStartedGameException;
import ru.wordle.domain.exception.WordNotInDictionaryException;
import ru.wordle.api.session.GameSession;
import ru.wordle.domain.model.GameStatus;
import ru.wordle.infrastructure.datastorage.Storage;
import ru.wordle.domain.model.Game;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class GameService {

    private final Storage storage;
    private final GameSession gameSession;

    public Game startNewGame() {
        String word = storage.getRandomWord();
        Game game = new Game(word);
        gameSession.set(game);
        return game;
    }

    public Game guess(String guess) {
        if (guess == null || guess.trim().isEmpty()) {
            throw new InvalidWordException("guess cannot be null or empty");
        }

        Game game = gameSession.get();
        if (game == null) {
            throw new UserNotStartedGameException();
        }

        if (game.getGameStatus() == GameStatus.WIN || game.getGameStatus() == GameStatus.LOSE) {
            throw new IllegalStateException("Game is finished.");
        }

        String normalized = guess.trim();

        if (!game.validateWord(normalized)) {
            throw new InvalidWordException("invalid word format");
        }

        String lower = normalized.toLowerCase(Locale.ROOT);

        if (!storage.isExists(lower)) {
            throw new WordNotInDictionaryException(lower);
        }

        synchronized (game) {
            game.makeAttempt(lower);
        }

        return game;
    }

    public Game getGame() {
        Game game = gameSession.get();
        if (game == null) {
            throw new UserNotStartedGameException();
        }
        return game;
    }
}

