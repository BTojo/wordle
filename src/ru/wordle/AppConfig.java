package ru.wordle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.wordle.datastorage.StorageException;
import ru.wordle.logic.Game;
import ru.wordle.presentation.OutputToConsole;
import ru.wordle.presentation.Storage;
import ru.wordle.presentation.WordleView;

import java.util.Scanner;

@Configuration
public class AppConfig {

    @Bean
    public Storage storage() throws StorageException {
        return new Storage();
    }


//    @Bean
//    public Game game(Storage storage) throws StorageException {
//        String randomWord = storage.getRandomWord();
//        return new Game(randomWord);
//    }

    @Bean
    public WordleView wordleView(Storage storage, OutputToConsole outputToConsole, Scanner scanner) throws StorageException {

        String randomWord = storage.getRandomWord();
        Game game = new Game(randomWord);
        WordleView wordleView = new WordleView(scanner);
        wordleView.setGame(game);
        return wordleView;
    }
    @Bean
    public OutputToConsole outputToConsole(Scanner scanner) {
        return new OutputToConsole((scanner));
    }

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }


}