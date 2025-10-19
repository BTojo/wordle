package ru.wordle.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public ResponseEntity<?> startNew() {
        try {
            GameDto dto = gameService.startNewGame();
            return ResponseEntity.status(201).body(dto);
        } catch (StorageException e) {
            return ResponseEntity.status(500).body("Dictionary loading error: " + e.getMessage());
        }
    }

    @PostMapping("/guess")
    public ResponseEntity<GameDto> guess(@Valid @RequestBody GuessRequest req) {
        GameDto dto = gameService.guess(req.getGuess());
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<GameDto> get() {
        return ResponseEntity.ok(gameService.getStatus());
    }
}