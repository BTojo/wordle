package ru.wordle.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wordle.domain.exception.InvalidCredentialsException;
import ru.wordle.domain.exception.LoginAlreadyTakenException;
import ru.wordle.domain.model.User;
import ru.wordle.domain.model.UserStatus;
import ru.wordle.domain.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(String login, String rawPassword) {
        if (userRepository.existsByLogin(login)) {
            throw new LoginAlreadyTakenException(login);
        }

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setLogin(login);
        user.setPassword(hashSha256(rawPassword));
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return user;
    }

    public User login(String login, String rawPassword) {
        String hashedPassword = hashSha256(rawPassword);

        User user = userRepository.findByLogin(login)
                .orElseThrow(InvalidCredentialsException::new);

        if (!hashedPassword.equals(user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return user;
    }

    private String hashSha256(String raw) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }
}