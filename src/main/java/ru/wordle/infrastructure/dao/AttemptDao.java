package ru.wordle.infrastructure.dao;

import ru.wordle.domain.model.Attempt;

public interface AttemptDao {

    void save(Attempt attempt);
}
