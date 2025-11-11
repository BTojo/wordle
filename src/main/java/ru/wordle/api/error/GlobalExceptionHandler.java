package ru.wordle.api.error;

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
import ru.wordle.domain.exception.GameAlreadyFinishedException;
import ru.wordle.domain.exception.InvalidWordException;
import ru.wordle.domain.exception.UserNotStartedGameException;
import ru.wordle.domain.exception.WordNotInDictionaryException;
import ru.wordle.infrastructure.datastorage.StorageException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fields = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a, b) -> a, LinkedHashMap::new));

        return new ErrorDto("validation_failed", null, fields);
    }

    @ExceptionHandler(InvalidWordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleInvalidWordException(InvalidWordException ex) {
        return new ErrorDto("invalid_word", ex.getMessage(), null);
    }

    @ExceptionHandler(WordNotInDictionaryException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorDto handleWordNotInDictionaryException(WordNotInDictionaryException ex) {
        return new ErrorDto("not_in_dictionary", ex.getMessage(), null);
    }

    @ExceptionHandler(GameAlreadyFinishedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleGameAlreadyFinished(GameAlreadyFinishedException ex) {
        return new ErrorDto("game_already_finished", ex.getMessage(), null);
    }

    @ExceptionHandler(StorageException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorDto handleStorageException(StorageException ex) {
        return new ErrorDto("storage_unavailable", ex.getMessage(), null);
    }

    @ExceptionHandler(UserNotStartedGameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto handleUserNotStarted(UserNotStartedGameException ex) {
        return new ErrorDto("game_not_started", ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleOther(Exception ex) {
        return new ErrorDto("internal_error", "unexpected error", null);
    }
}
