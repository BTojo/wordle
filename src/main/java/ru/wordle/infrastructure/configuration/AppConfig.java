package ru.wordle.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.wordle.infrastructure.datastorage.StorageException;
import ru.wordle.domain.model.Game;
import ru.wordle.domain.model.GameFactory;
import ru.wordle.infrastructure.presentation.OutputToConsole;
import ru.wordle.infrastructure.datastorage.Storage;
import ru.wordle.infrastructure.presentation.WordleView;

import java.util.Scanner;

@Configuration
public class AppConfig {

    @Bean
    public Storage storage() throws StorageException {
        return new Storage();
    }

    @Bean
    public GameFactory gameFactory(Storage storage) {
        return new GameFactory(storage);
    }

    @Bean
    public WordleView wordleView(Storage storage, OutputToConsole outputToConsole, Scanner scanner, GameFactory gameFactory) throws StorageException {
        Game game = gameFactory.create();
        return new WordleView(scanner, outputToConsole, gameFactory, storage, game);
    }

    @Bean
    public OutputToConsole outputToConsole(Scanner scanner) {
        return new OutputToConsole(scanner);
    }

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }
}