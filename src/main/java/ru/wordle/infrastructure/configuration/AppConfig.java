package ru.wordle.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.wordle.infrastructure.datastorage.StorageException;
import ru.wordle.domain.model.Game;
import ru.wordle.domain.model.GameFactory;
import ru.wordle.infrastructure.datastorage.Storage;

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
    public Scanner scanner() {
        return new Scanner(System.in);
    }
}