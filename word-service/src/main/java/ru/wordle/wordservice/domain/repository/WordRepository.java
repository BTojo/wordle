package ru.wordle.wordservice.domain.repository;

public interface WordRepository {
    String getRandomWord();

    boolean isExists(String word);
}