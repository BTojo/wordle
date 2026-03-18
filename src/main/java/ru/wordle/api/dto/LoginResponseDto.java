package ru.wordle.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class LoginResponseDto {

    private UUID userId;
    private String login;
}