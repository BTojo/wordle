package ru.wordle.infrastructure.repository;

public interface WordRepository {
    String getRandomWord();

    boolean isExists(String str);
}
