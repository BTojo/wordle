package ru.wordle.domain.repository;

public interface WordRepository {
    String getRandomWord();

    boolean isExists(String str);
}
