package ru.wordle.domain.repository;

import ru.wordle.domain.model.Game;
import java.util.Optional;

public interface GameRepository {
    Game save(Game game);

    Optional<Game> findById(String gameId);

}
