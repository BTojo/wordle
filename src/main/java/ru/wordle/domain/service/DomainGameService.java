package ru.wordle.domain.service;

import org.springframework.stereotype.Service;
import ru.wordle.domain.exception.InvalidWordException;
import ru.wordle.domain.exception.GameAlreadyFinishedException;
import ru.wordle.domain.model.Game;
import ru.wordle.domain.model.GameStatus;

@Service
public class DomainGameService {

    public Game createGame(String secretWord) {
        if (secretWord == null || secretWord.trim().isEmpty()) {
            throw new InvalidWordException("secret word cannot be null or empty");
        }
        if (secretWord.length() != 5) {
            throw new InvalidWordException("secret word must be 5 letters");
        }
        return new Game(secretWord.toLowerCase());
    }

    public Game makeAttempt(Game game, String guess) {
        if (game == null) {
            throw new IllegalArgumentException("game cannot be null");
        }
        if (game.getGameStatus() == GameStatus.WIN || game.getGameStatus() == GameStatus.LOSE) {
            throw new GameAlreadyFinishedException();
        }

        if (guess == null || guess.trim().isEmpty()) {
            throw new InvalidWordException("guess cannot be null or empty");
        }

        String normalized = guess.trim();
        if (normalized.length() != 5) {
            throw new InvalidWordException("guess must be 5 letters");
        }
        if (!normalized.matches("^[A-Za-z]+$")) {
            throw new InvalidWordException("guess must contain only letters");
        }

        String lower = normalized.toLowerCase();

        game.makeAttempt(lower);
        return game;
    }
}
