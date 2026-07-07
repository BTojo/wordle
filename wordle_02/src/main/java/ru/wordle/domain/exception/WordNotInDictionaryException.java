package ru.wordle.domain.exception;

public class WordNotInDictionaryException extends RuntimeException {
    public WordNotInDictionaryException(String word) {
        super("word '" + word + "' not found in dictionary");
    }

    public WordNotInDictionaryException() {
        super("word not found in dictionary");
    }
}
