package ru.wordle.infrastructure.repository;

import ru.wordle.domain.model.Game;

public interface GameRepository {
    void save(Game game);
}
