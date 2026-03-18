package ru.wordle.api.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import ru.wordle.api.dto.ErrorDto;
import ru.wordle.domain.exception.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleValidation(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);

        Map<String, String> fields = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        return new ErrorDto("validation_failed", null, fields);
    }

    @ExceptionHandler(AttemptValidateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleAttemptValidate(AttemptValidateException ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorDto("invalid_attempt", ex.getError().name(), null);
    }

    @ExceptionHandler(MakeAttemptException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleMakeAttempt(MakeAttemptException ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorDto("attempt_failed", ex.getError().name(), null);
    }

    @ExceptionHandler(UserNotStartedGameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto handleUserNotStarted(UserNotStartedGameException ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorDto("game_not_started", ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleOther(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorDto("internal_error", "unexpected error", null);
    }

    @ExceptionHandler(LoginAlreadyTakenException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto handleLoginAlreadyTaken(LoginAlreadyTakenException ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorDto("login_already_taken", ex.getMessage(), null);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleInvalidCredentials(InvalidCredentialsException ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorDto("invalid_credentials", ex.getMessage(), null);
    }
}