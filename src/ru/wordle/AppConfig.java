package ru.wordle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.wordle.datastorage.StorageException;
import ru.wordle.logic.Game;
import ru.wordle.presentation.Storage;
import ru.wordle.presentation.WordleView;

@Configuration
public class AppConfig {

    @Bean
    public Storage storage() throws StorageException {
        return new Storage();
    }

    @Bean
    public Game game(Storage storage) throws StorageException {
        String randomWord = storage.getRandomWord();
        return new Game(randomWord);
    }

    @Bean
    public WordleView wordleView(Game game) throws StorageException {
        WordleView wordleView = new WordleView();
        wordleView.setGame(game);
        return wordleView;
    }


}