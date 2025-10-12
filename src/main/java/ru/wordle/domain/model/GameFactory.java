package ru.wordle.domain.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.wordle.infrastructure.datastorage.Storage;

@Component
public class GameFactory {

    private final Storage storage;

    @Autowired
    public  GameFactory (Storage storage) {
        this.storage = storage;
    }

    public Game create() {
        String randomWorld = storage.getRandomWord();
        return new Game(randomWorld);
    }
}
