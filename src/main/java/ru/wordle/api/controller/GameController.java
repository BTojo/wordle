package ru.wordle.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.wordle.api.mapper.GameMapper;
import ru.wordle.domain.model.Game;
import ru.wordle.domain.service.GameService;
import ru.wordle.api.dto.GuessRequest;
import ru.wordle.api.dto.GameDto;
import ru.wordle.infrastructure.datastorage.StorageException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final GameMapper gameMapper;

    @PostMapping
    public ResponseEntity<GameDto> startNew() throws StorageException {
        Game game = gameService.startNewGame();
        return ResponseEntity.status(201).body(gameMapper.toDto(game));
        }

    @PostMapping("/guess")
    public ResponseEntity<GameDto> guess(@Valid @RequestBody GuessRequest req) {
        Game game = gameService.guess(req.getGuess());
        return ResponseEntity.ok(gameMapper.toDto(game));
    }

    @GetMapping
    public ResponseEntity<GameDto> get() {
        Game game = gameService.getStatus();
        return ResponseEntity.ok(gameMapper.toDto(game));
    }
}