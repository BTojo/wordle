package ru.wordle.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.wordle.api.session.UserSession;
import ru.wordle.domain.model.User;
import ru.wordle.domain.service.UserService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserSession userSession;

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout() {
        log.info("User logged out, userId={}", userSession.getUserId());
        userSession.setUserId(null);
    }

    @GetMapping("/me")
    public Map<String, String> me() {
        if (!userSession.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }
        User user = userService.findById(userSession.getUserId());
        log.info("GET /me, userId={}", userSession.getUserId());
        return Map.of("username", user.getLogin());
    }
}