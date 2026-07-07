package ru.wordle.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.wordle.domain.model.UserStatus;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class RegistrationResponseDto {

    private UUID userId;
    private String login;
    private UserStatus status;
}