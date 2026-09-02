package ru.wordle.wordservice.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.wordle.wordservice.api.dto.RandomWordResponseDto;
import ru.wordle.wordservice.api.dto.WordExistsResponseDto;
import ru.wordle.wordservice.domain.service.WordService;

@RestController
@RequestMapping("/api/words")
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;

    @GetMapping("/random")
    public RandomWordResponseDto getRandomWord() {
        return new RandomWordResponseDto(wordService.getRandomWord());
    }

    @GetMapping("/exists")
    public WordExistsResponseDto checkExists(@RequestParam String word) {
        return new WordExistsResponseDto(wordService.isExists(word));
    }
}