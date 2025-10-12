package ru.wordle;

import ru.wordle.infrastructure.datastorage.StorageException;
import ru.wordle.infrastructure.presentation.WordleView;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.wordle.infrastructure.configuration.AppConfig;

public class Main {

    public static void main(String[] args) throws StorageException {
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            WordleView wordleView = context.getBean(WordleView.class);
            wordleView.start();
        }
    }
}