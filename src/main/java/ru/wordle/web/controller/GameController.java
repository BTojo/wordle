package ru.wordle.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.wordle.service.GameService;
import ru.wordle.web.dto.GuessRequest;
import ru.wordle.web.dto.GameDto;
import ru.wordle.datastorage.StorageException;

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
    public ResponseEntity<GameDto> guess(@RequestBody GuessRequest req) {
        GameDto dto = gameService.guess(req.getGuess());
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<GameDto> get() {
        return ResponseEntity.ok(gameService.getStatus());
    }
}