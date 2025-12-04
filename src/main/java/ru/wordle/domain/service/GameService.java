package ru.wordle.domain.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wordle.domain.model.Game;

@Service
@RequiredArgsConstructor
public class GameService {
    private final ApplicationGameService applicationGameService;

    public Game startNewGame() {
        return applicationGameService.startNewGame();
    }

    public Game guess(String guess) {
        return applicationGameService.guess(guess);
    }

    public Game getGame() {
        return applicationGameService.getCurrentGame();
    }
}

