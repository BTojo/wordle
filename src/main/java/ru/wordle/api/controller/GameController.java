package ru.wordle.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.wordle.api.dto.GameDto;
import ru.wordle.api.mapper.GameMapper;
import ru.wordle.api.session.GameSession;
import ru.wordle.domain.service.ApplicationGameService;
import ru.wordle.api.dto.GuessRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {
    private static final Logger log = LoggerFactory.getLogger(GameController.class);

    private final ApplicationGameService applicationService;
    private final GameMapper gameMapper;
    private final GameSession gameSession;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDto startNewGame() {
        var game = applicationService.startNewGame();

        gameSession.setGameId(game.getGameId());

        log.info("New game started, gameId={}", game.getGameId());
        return gameMapper.toDto(game);
    }

    @PostMapping("/guess")
    public GameDto guess(@Valid @RequestBody GuessRequestDto request) {
        String gameId = gameSession.getGameId();

        var game = applicationService.guess(gameId, request.getGuess());
        return gameMapper.toDto(game);
    }

    @GetMapping
    public GameDto getGame() {
        String gameId = gameSession.getGameId();
        var game = applicationService.getCurrentGame(gameId);
        return gameMapper.toDto(game);
    }
}
