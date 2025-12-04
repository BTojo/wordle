package ru.wordle.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.wordle.api.dto.GameDto;
import ru.wordle.api.mapper.GameMapper;
import ru.wordle.domain.service.ApplicationGameService;
import ru.wordle.api.dto.GuessRequestDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final ApplicationGameService applicationService;  // Только application service
    private final GameMapper gameMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDto startNewGame() {
        var game = applicationService.startNewGame();
        return gameMapper.toDto(game);
    }

    @PostMapping("/guess")
    public GameDto guess(@Valid @RequestBody GuessRequestDto request) {
        var game = applicationService.guess(request.getGuess());
        return gameMapper.toDto(game);
    }

    @GetMapping
    public GameDto getGame() {
        var game = applicationService.getCurrentGame();
        return gameMapper.toDto(game);
    }
}
