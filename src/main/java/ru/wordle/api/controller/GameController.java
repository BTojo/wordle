package ru.wordle.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.wordle.api.dto.GameDto;
import ru.wordle.api.dto.GuessRequestDto;
import ru.wordle.api.mapper.GameDtoMapper;
import ru.wordle.api.session.GameSession;
import ru.wordle.api.session.UserSession;
import ru.wordle.domain.model.Game;
import ru.wordle.domain.service.ApplicationGameService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final ApplicationGameService applicationService;
    private final GameSession gameSession;
    private final UserSession userSession;
    private final GameDtoMapper gameDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDto startNewGame() {
        String ownerId = userSession.isAuthenticated()
                ? userSession.getUserId().toString()
                : null;
        Game game = applicationService.startNewGame(ownerId);
        gameSession.setGameId(game.getGameId());
        log.info("New game started, gameId={}, ownerId={}", game.getGameId(), ownerId);
        return gameDtoMapper.toDto(game);
    }

    @GetMapping
    public GameDto getGame() {
        String gameId = gameSession.getGameId();
        var game = applicationService.getCurrentGame(gameId);
        return gameDtoMapper.toDto(game);
    }

    @PostMapping("/guess")
    public GameDto attempt(@Valid @RequestBody GuessRequestDto request) {
        String gameId = gameSession.getGameId();
        var game = applicationService.guess(gameId, request.getGuess());
        return gameDtoMapper.toDto(game);
    }
}