package ru.wordle.domain.model;

import org.springframework.stereotype.Component;
import ru.wordle.infrastructure.repository.WordRepository; // Импортируй свой интерфейс

@Component
public class GameFactory {

    private final WordRepository wordRepository;


    public GameFactory(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public Game create() {
        String randomWord = wordRepository.getRandomWord();
        return new Game(randomWord);
    }
}
