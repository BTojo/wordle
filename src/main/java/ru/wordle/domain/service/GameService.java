package ru.wordle.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wordle.api.error.BadRequestException;
import ru.wordle.api.error.UnprocessableEntityException;
import ru.wordle.api.error.UserNotStartedGameException;
import ru.wordle.api.session.GameSession;
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
        Game game = gameSession.get();
        if (game == null) {
            throw new UserNotStartedGameException();
        }

        String normalized = guess.trim();

        if (!game.validateWord(normalized)) {
            throw new BadRequestException("invalid word format");
        }

        String lower = normalized.toLowerCase(Locale.ROOT);

        if (!storage.isExists(lower)) {
            throw new UnprocessableEntityException("word not found in dictionary");
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

