package ru.wordle.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.wordle.api.dto.GuessRequestDto;
import ru.wordle.api.mapper.GameMapper;
import ru.wordle.domain.model.Game;
import ru.wordle.domain.service.GameService;
import ru.wordle.api.dto.GameDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final GameMapper gameMapper;

    @PostMapping
    public GameDto startNew() {
        Game game = gameService.startNewGame();
        return gameMapper.toDto(game);
        }

    @PostMapping("/guess")
    public GameDto guess(@Valid @RequestBody GuessRequestDto req) {
        Game game = gameService.guess(req.getGuess());
        return gameMapper.toDto(game);
    }

    @GetMapping
    public GameDto get() {
        Game game = gameService.getGame();
        return gameMapper.toDto(game);
    }
}