package ru.wordle.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.wordle.api.dto.LoginRequestDto;
import ru.wordle.api.dto.LoginResponseDto;
import ru.wordle.api.dto.RegistrationRequestDto;
import ru.wordle.api.dto.RegistrationResponseDto;
import ru.wordle.api.session.UserSession;
import ru.wordle.domain.model.User;
import ru.wordle.domain.service.UserService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserSession userSession;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponseDto register(@Valid @RequestBody RegistrationRequestDto request) {
        log.info("Registration attempt for login={}", request.getLogin());
        User user = userService.register(request.getLogin(), request.getPassword());
        log.info("User registered successfully, userId={}", user.getId());
        return new RegistrationResponseDto(user.getId(), user.getLogin(), user.getStatus());
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto request) {
        log.info("Login attempt for login={}", request.getLogin());
        User user = userService.login(request.getLogin(), request.getPassword());
        userSession.setUserId(user.getId());
        log.info("User logged in successfully, userId={}", user.getId());
        return new LoginResponseDto(user.getId(), user.getLogin());
    }
}