package ru.wordle.wordservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RandomWordResponseDto {
    private String word;
}