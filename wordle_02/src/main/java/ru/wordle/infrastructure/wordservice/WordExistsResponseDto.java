package ru.wordle.infrastructure.wordservice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordExistsResponseDto {
    private boolean exists;
}