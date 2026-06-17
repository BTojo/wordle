package ru.wordle.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
@Getter
@Setter
public class GuessRequestDto {
    @NotBlank(message = "word must not be blank")
    @Size(min = 5, max = 5, message = "word must be exactly 5 letters")
    private String word;

}
