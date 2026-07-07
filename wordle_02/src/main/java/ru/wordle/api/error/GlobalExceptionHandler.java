package ru.wordle.api.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.wordle.api.dto.ErrorDto;
import ru.wordle.domain.exception.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorDto> handleValidation(MethodArgumentNotValidException ex) {
        log.error("Validation failed: {}", ex.getMessage());
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .map(ErrorDto::new)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(AttemptValidateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorDto> handleAttemptValidate(AttemptValidateException ex) {
        log.error("Attempt validate error: {}", ex.getMessage());
        return List.of(new ErrorDto(ex.getError().name()));
    }

    @ExceptionHandler(MakeAttemptException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorDto> handleMakeAttempt(MakeAttemptException ex) {
        log.error("Make attempt error: {}", ex.getMessage());
        return List.of(new ErrorDto(ex.getError().name()));
    }

    @ExceptionHandler(UserNotStartedGameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public List<ErrorDto> handleUserNotStarted(UserNotStartedGameException ex) {
        log.error("Game not started: {}", ex.getMessage());
        return List.of(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(LoginAlreadyTakenException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public List<ErrorDto> handleLoginAlreadyTaken(LoginAlreadyTakenException ex) {
        log.error("Login already taken: {}", ex.getMessage());
        return List.of(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public List<ErrorDto> handleInvalidCredentials(InvalidCredentialsException ex) {
        log.error("Invalid credentials: {}", ex.getMessage());
        return List.of(new ErrorDto("Invalid login or password"));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public List<ErrorDto> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.error("Data integrity violation: {}", ex.getMessage());
        return List.of(new ErrorDto("Login already taken"));
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public List<ErrorDto> handleOther(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return List.of(new ErrorDto("Internal server error"));
    }
}