package ru.wordle;

import ru.wordle.datastorage.StorageException;
import ru.wordle.presentation.WordleView;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) throws StorageException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        WordleView wordleView = context.getBean(WordleView.class);
        wordleView.start();
        context.close();
    }
}