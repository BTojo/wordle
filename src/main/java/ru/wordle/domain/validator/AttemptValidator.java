package ru.wordle.domain.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.wordle.domain.repository.WordRepository;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class AttemptValidator {

    private final WordRepository wordRepository;

    public AttemptValidateError validate(String guess) {
        if (guess == null) {
            return AttemptValidateError.INVALID_LENGTH;
        }

        String normalized = guess.trim();
        if (normalized.length() != 5) {
            return AttemptValidateError.INVALID_LENGTH;
        }
        if (!normalized.matches("^[A-Za-z]+$")) {
            return AttemptValidateError.INVALID_ALPHABET;
        }

        String lower = normalized.toLowerCase(Locale.ROOT);
        if (!wordRepository.isExists(lower)) {
            return AttemptValidateError.WORD_NOT_EXISTS;
        }

        return null;
    }
}