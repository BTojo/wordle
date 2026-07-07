package ru.wordle.domain.repository;

import ru.wordle.domain.model.Attempt;

public interface AttemptRepository {

    void save(Attempt attempt);
}


