package ru.wordle.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wordle.api.error.BadRequestException;
import ru.wordle.api.error.UnprocessableEntityException;
import ru.wordle.infrastructure.datastorage.Storage;
import ru.wordle.infrastructure.datastorage.StorageException;
import ru.wordle.domain.model.Game;
import ru.wordle.api.dto.GameDto;
import ru.wordle.api.dto.LetterDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final Storage storage;
    private Game currentGame;

    public GameDto startNewGame() throws StorageException {
        String word = storage.getRandomWord();
        currentGame = new Game(word);
        return toDto(currentGame);
    }

    public GameDto guess(String guess) {
        if (currentGame == null) {
            throw new BadRequestException("game not started");
        }

        String normalized = guess.trim();

        if (!currentGame.validateWord(normalized)) {
            throw new BadRequestException("invalid word format");
        }

        if (!storage.isExists(normalized.toLowerCase())) {
            throw new UnprocessableEntityException("word not found in dictionary");
        }
        currentGame.makeAttempt(normalized.toLowerCase());
        return toDto(currentGame);
    }

    public GameDto getStatus() {
        if (currentGame == null) {
            throw new IllegalStateException("The game hasn't started yet");
        }
        return toDto(currentGame);
    }

    private GameDto toDto(Game game) {
        List<List<LetterDto>> attempts = game.getAttemptsList().stream()
                .map(attempt -> attempt.getLetters().stream()
                        .map(letter -> new LetterDto(
                                letter.getValue(),
                                letter.getStatus().name()
                        ))
                        .toList()
                )
                .toList();

        return new GameDto(
                game.getGameStatus().name(),
                attempts);
    }
}