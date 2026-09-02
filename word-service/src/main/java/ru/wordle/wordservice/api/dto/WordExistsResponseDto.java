package ru.wordle.wordservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WordExistsResponseDto {
    private boolean exists;
}