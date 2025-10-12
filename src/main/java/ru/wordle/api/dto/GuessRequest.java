package ru.wordle.api.dto;

import lombok.Data;

@Data
public class GuessRequest {
    private String guess;
}
