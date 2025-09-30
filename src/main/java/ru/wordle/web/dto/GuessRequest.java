package ru.wordle.web.dto;

import lombok.Data;

@Data
public class GuessRequest {
    private String guess;
}
