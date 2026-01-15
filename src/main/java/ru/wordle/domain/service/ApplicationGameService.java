package ru.wordle.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wordle.api.session.GameSession;
import ru.wordle.domain.exception.InvalidWordException;
import ru.wordle.domain.exception.UserNotStartedGameException;
import ru.wordle.domain.exception.WordNotInDictionaryException;
import ru.wordle.domain.model.Game;
import ru.wordle.infrastructure.repository.GameRepository;
import ru.wordle.infrastructure.repository.WordRepository;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationGameService {

    private final DomainGameService domainService;
    private final WordRepository wordRepository;
    private final GameRepository gameRepository;
    private final GameSession gameSession;

    public Game startNewGame() {
        String secretWord = wordRepository.getRandomWord();

        Game game = domainService.createGame(secretWord);

        String gameId = UUID.randomUUID().toString();
        game.setGameId(gameId);

        gameRepository.save(game);

        gameSession.set(game);
        return game;
    }

    public Game guess(String guess) {
        Game game = gameSession.get();
        if (game == null) {
            throw new UserNotStartedGameException();
        }

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

        Game updatedGame = domainService.makeAttempt(game, lower);

        gameRepository.save(updatedGame);
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
