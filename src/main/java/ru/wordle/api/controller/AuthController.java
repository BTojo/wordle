package ru.wordle.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.wordle.api.dto.LoginRequestDto;
import ru.wordle.api.dto.LoginResponseDto;
import ru.wordle.api.session.UserSession;
import ru.wordle.domain.model.User;
import ru.wordle.domain.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserSession userSession;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto request) {
        log.info("Login attempt for login={}", request.getLogin());
        User user = userService.login(request.getLogin(), request.getPassword());
        userSession.setUserId(user.getId());
        log.info("User logged in successfully, userId={}", user.getId());
        return new LoginResponseDto(user.getId(), user.getLogin());
    }


    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() {
        if (userSession.isAuthenticated()) {
            log.info("User logged out, userId={}", userSession.getUserId());
        }
        userSession.setUserId(null);
    }

    @GetMapping("/me")
    public Map<String, Object> me() {
        if (!userSession.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }
        log.info("Getting current user, userId={}", userSession.getUserId());
        return Map.of("userId", userSession.getUserId());
    }
}