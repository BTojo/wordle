package ru.wordle.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wordle.api.error.BadRequestException;
import ru.wordle.api.error.UnprocessableEntityException;
import ru.wordle.api.session.GameSession;
import ru.wordle.infrastructure.datastorage.Storage;
import ru.wordle.infrastructure.datastorage.StorageException;
import ru.wordle.domain.model.Game;


import java.util.List;
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
            throw new BadRequestException("game not started");
        }

        String normalized = guess.trim();

        if (!game.validateWord(normalized)) {
            throw new BadRequestException("invalid word format");
        }

        String lower = normalized.toLowerCase(Locale.ROOT);

        if (!storage.isExists(normalized.toLowerCase())) {
            throw new UnprocessableEntityException("word not found in dictionary");
        }

        synchronized (game) {
            game.makeAttempt(lower);
        }

        return game;
    }

    public Game getStatus() {
        Game game = gameSession.get();
        if (game == null) {
            throw new IllegalStateException("The game hasn't started yet");
        }
        return game;
    }
}

