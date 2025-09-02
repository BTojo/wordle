package ru.wordle.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.wordle.presentation.Storage;

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

    public Storage getStorage() {
        return storage;
    }
}
