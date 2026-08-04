package ru.wordle.wordservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.wordle.wordservice.dto.RandomWordResponseDto;
import ru.wordle.wordservice.dto.WordExistsResponseDto;
import ru.wordle.wordservice.service.WordService;

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