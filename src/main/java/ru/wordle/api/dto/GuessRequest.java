package ru.wordle.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
public class GuessRequest {
    @NotBlank(message = "guess must not be blank")
    @Size(min = 5, max = 5, message = "guess must be 5 letters")
    @Pattern(regexp = "^[A-Za-z] +$", message = "guess must contain only letters")

    private String guess;
}
