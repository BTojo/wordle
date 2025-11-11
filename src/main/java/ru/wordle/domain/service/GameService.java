package ru.wordle.domain.service;


import org.springframework.stereotype.Service;
import ru.wordle.application.service.ApplicationGameService;
import ru.wordle.domain.model.Game;

@Service
public class GameService {
    private final ApplicationGameService applicationGameService;

    public GameService(ApplicationGameService applicationGameService) {
        this.applicationGameService = applicationGameService;
    }

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

